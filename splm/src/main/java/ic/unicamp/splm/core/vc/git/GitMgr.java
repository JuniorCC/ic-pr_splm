package ic.unicamp.splm.core.vc.git;

import ic.unicamp.splm.core.util.dir.GitDir;
import ic.unicamp.splm.core.util.logger.SplMgrLogger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.IOException;
import java.util.stream.Collectors;

import static ic.unicamp.splm.core.util.msg.ErrorMsgTag.ERR_0__CREATING_JGIT_OBJ;
import static ic.unicamp.splm.core.util.msg.WarnMsgTag.WAR_0__BRANCH_NAME_IS_BEEING_USED_IN_THE_LOCAL_GIT;
import static ic.unicamp.splm.core.util.msg.WarnMsgTag.WAR_0__PARENT_GIT_BRANCH_DOES_NOT_EXITS;

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
  }

  public void createBaseBranch(String name) {
    createBranch("Master", name);
  }

  public void createBranch(String parent, String branch) {
    if (__exitsLocalBranch(parent)) {
      try {
        if (!__exitsLocalBranch(branch)) {
          git.checkout().setName(parent).call();
          git.branchCreate().setName(branch).call();
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
      exits =
          !git.branchList().call().stream()
              .map(Ref::getName)
              .collect(Collectors.toList())
              .contains("refs/heads/" + name);
    } catch (GitAPIException e) {
      e.printStackTrace();
    }
    return exits;
  }
}
