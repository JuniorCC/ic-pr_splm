package ic.unicamp.splm.core.vc.git;

import ic.unicamp.splm.core.util.dir.GitDir;
import ic.unicamp.splm.core.util.logger.SplMgrLogger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.IOException;

import static ic.unicamp.splm.core.util.msg.ErrorMsgTag.ERR_0__CREATING_JGIT_OBJ;

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
}
