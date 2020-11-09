package ic.unicamp.splm.my_samples;

import ic.unicamp.splm.core.util.dir.GitDir;
import ic.unicamp.splm.core.util.logger.SplMgrLogger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static ic.unicamp.splm.core.util.msg.ErrorMsgTag.ERR_0__CREATING_JGIT_OBJ;
import static ic.unicamp.splm.core.util.msg.WarnMsgTag.WAR_0__BRANCH_NAME_IS_BEEING_USED_IN_THE_LOCAL_GIT;
import static ic.unicamp.splm.core.util.msg.WarnMsgTag.WAR_0__PARENT_GIT_BRANCH_DOES_NOT_EXITS;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class GItCreateBranch
{
    Git git;
  @Before
  public void setUp() throws Exception {
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

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {


        assertTrue( true );
        String parent = "master";
        String branch = "B_1";
        if (__exitsLocalBranch(parent)) {
            try {
                if (!__exitsLocalBranch(branch)) {
                    if(parent.equals("master")){
                        git.branchCreate().setName(branch).call();
                    }else{
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
        if(name.equals("master")) return true;
        boolean exits = false;
        try {
            List<String> gitBranchList1 = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call().stream().map(Ref::getName).collect(Collectors.toList());
            List<String> gitBranchList2 = git.branchList().call().stream()
                    .map(Ref::getName)
                    .collect(Collectors.toList());
            exits = gitBranchList1.contains("refs/heads/" + name);
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return exits;
    }
}
