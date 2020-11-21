package ic.unicamp.splm.core.vc.git;

import ic.unicamp.splm.core.util.dir.GitDir;
import ic.unicamp.splm.core.util.dir.GitUtil;
import ic.unicamp.splm.core.util.logger.SplMgrLogger;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeCommand;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static ic.unicamp.splm.core.util.msg.ErrorMsgTag.ERR_0__CREATING_JGIT_OBJ;
import static ic.unicamp.splm.core.util.msg.InfoMsgTag.*;
import static ic.unicamp.splm.core.util.msg.WarnMsgTag.*;

public class GitMgr {
    Git git;

    public void init() {
        FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
        repositoryBuilder.setMustExist(true);
        repositoryBuilder.setGitDir(GitDir.get_git_dir__as_file());
        try {
            Repository repository = repositoryBuilder.build();
            this.git = new Git(repository);
        } catch (IOException e) {
            SplMgrLogger.error(ERR_0__CREATING_JGIT_OBJ, false);
            e.printStackTrace();
        }
        if (!__exitsGitLocalBranch("master")) {
            File myFile =
                    new File(git.getRepository().getDirectory().getParent(), ".gitignore");
            try {
                if (!myFile.createNewFile()) {
                    SplMgrLogger.warn(WAR_0__GIT_IGNORE_HELLO_SPLM_WAS_NOT_CREATED, true);
                } else {
                    FileUtils.writeStringToFile(myFile, GitUtil.retrieve_git_ignore_content(), "ISO-8859-1");
                    this.git.add().addFilepattern(".gitignore").call();
                    this.git.commit().setMessage("SPLM: Init master branch with a commit").call();
                    SplMgrLogger.info(INF_0__MASTER_BRANCH_CREATED, true);
                }
            } catch (IOException | GitAPIException e) {
                e.printStackTrace();
            }
        }
    }

    public void createBaseBranch(String name) {
        createBranch("master", name);
    }

    public void createBranch(String parent, String branch) {
        if (__exitsGitLocalBranch(parent)) {
            try {
                if (!__exitsGitLocalBranch(branch)) {
                    if (parent.equals("master")) {
                        git.branchCreate().setName(branch).call();
                    } else {
                        git.branchCreate().setName(branch).setStartPoint(parent).setForce(true).call();
                    }
                    SplMgrLogger.info(
                            String.format(INF_0__CREATED_BRANCH_FROM, branch, parent), true);
                } else {
                    SplMgrLogger.error(
                            String.format(WAR_0__BRANCH_NAME_IS_BEEING_USED_IN_THE_LOCAL_GIT, branch), true);
                }
            } catch (GitAPIException e) {
                e.printStackTrace();
            }
        } else {

            SplMgrLogger.error(String.format(WAR_0__PARENT_GIT_BRANCH_DOES_NOT_EXITS, parent), true);
        }
    }

    private boolean __exitsGitLocalBranch(String name) {
        boolean exits = false;
        try {
            // List<String> gitBranchList1 =
            // git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call().stream().map(Ref::getName).collect(Collectors.toList());
            List<String> gitBranchList =
                    git.branchList().call().stream().map(Ref::getName).collect(Collectors.toList());
            exits = gitBranchList.contains("refs/heads/" + name);
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return exits;
    }

    public String getCurrentBranch() {
        try {
            String branch = git.getRepository().getFullBranch();
            return GitUtil.retrieve_name(branch);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void checkConflict(String product_name, String br_origin, List<String> branches) {
        branches.remove(br_origin); // remove my origin

        String branch_name = GitUtil.create_merge_tag(product_name);
        try {
            git.branchCreate().setName(branch_name).setStartPoint(br_origin).call();

            CheckoutCommand coCmd = git.checkout();
            coCmd.setName(br_origin);
            coCmd.call();

            for (String branch:branches) {
                MergeCommand mgCmd = git.merge();
                Ref ref_branch = __retrieveRefFromBranch(branch);
                mgCmd.include(ref_branch); // "foo" is considered as a Ref to a branch
                MergeResult res = mgCmd.call();
                if (res.getMergeStatus().equals(MergeResult.MergeStatus.CONFLICTING)){
                    String conflicts = res.getConflicts().toString();
                    SplMgrLogger.info(
                            String.format(INF_0__YOU_COMMIT_GENERATE_CONFLICTS_WITH_PRODUCT, br_origin, branch, product_name, conflicts), true);
                    break;
                }
            }

            git.branchDelete().setBranchNames(branch_name).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    private Ref __retrieveRefFromBranch(String branch_name){
        List<Ref> branches = null;
        try {
            branches = git.branchList().call();
            for(Ref branch : branches) {
                if(branch.getName().equals(branch_name)){
                    return branch;
                }
            }
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return null;
    }
}
