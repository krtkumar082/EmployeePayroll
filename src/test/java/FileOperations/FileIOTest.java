package FileOperations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;


import org.junit.Assert;
import org.junit.Test;

public class FileIOTest {
    private static String HOME=System.getProperty("user.home");
    private static String PLAY_WITH_IO="TempPlayGround";
    
    @Test
    public void givenPathWhenCheckThenConfirm() throws IOException {
    	//check file exist
    	Path homePath=Paths.get(HOME);
    	Assert.assertTrue(Files.exists(homePath));
    	 
    	//Delete file and check not exist
    	Path playPath=Paths.get(HOME+"/"+"TempPlayGround");
    	if(Files.exists(playPath))
    		FileUtils.deleteFiles(playPath.toFile());
    	Assert.assertTrue(Files.notExists(playPath));
    	
    	//create directory
    	Files.createDirectory(playPath);
    	Assert.assertTrue(Files.exists(playPath));
    	
    	//create files
    	IntStream.range(1,10).forEach(cntr->{
    		Path tempFile=Paths.get(playPath+"/Temp"+cntr);
    		Assert.assertTrue(Files.notExists(tempFile));
    		try {Files.createFile(tempFile);}
    		catch(IOException e) { }
    		Assert.assertTrue(Files.exists(tempFile));
    	});
    	
    }
}
