package com.example.demo;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

public class GitTest {
    @Test
    public void gitTest() throws GitAPIException, IOException {
        final String REMOTE_URL="https://github.com/fingdori/clipper_wrapper.git";
        final File localPath = new File("/Users/sanghyunbak/work/git_test/");
        FileUtils.deleteDirectory(localPath);
        localPath.mkdirs();
        Git git = Git.cloneRepository()
                .setURI(REMOTE_URL)
                .setDirectory(localPath)
                .call();

        git.checkout().setName("refs/tags/hahaha").call();
        git.getRepository().close();

    }
}
