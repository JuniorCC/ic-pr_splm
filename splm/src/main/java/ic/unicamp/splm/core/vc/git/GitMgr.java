package ic.unicamp.splm.core.vc.git;

import ic.unicamp.splm.core.util.dir.GitDir;
import ic.unicamp.splm.core.util.logger.SplMgrLogger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static ic.unicamp.splm.core.util.msg.ErrorMsgTag.ERR_0__CREATING_JGIT_OBJ;
import static ic.unicamp.splm.core.util.msg.InfoMsgTag.INF_0__MASTER_BRANCH_CREATED;
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
    if (!__exitsLocalBranch("master")) {
      File myFile =
          new File(git.getRepository().getDirectory().getParent(), ".gitignore.hello.splm");
      try {
        if (!myFile.createNewFile()) {
          SplMgrLogger.warn(WAR_0__GIT_IGNORE_HELLO_SPLM_WAS_NOT_CREATED, true);
        } else {
          this.git.add().addFilepattern(".gitignore.hello.splm").call();
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
    if (__exitsLocalBranch(parent)) {
      try {
        if (!__exitsLocalBranch(branch)) {
          if (parent.equals("master")) {
            git.branchCreate().setName(branch).call();
          } else {
            git.branchCreate().setName(branch).setStartPoint(parent).setForce(true).call();
          }

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

  private boolean __exitsLocalBranch(String name) {
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
}
