package mx.pm.sat.ape1.dtsbot.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import mx.pm.sat.ape1.dtsbot.util.exception.SoUtilException;

@Slf4j
public abstract class SoUtil {
    public static int runningSoInstructions(List<String> command/*, String workingDirStr*/) throws SoUtilException {
        try {
            // Define the command and arguments
            /*
            List<String> command = new ArrayList<>();
            command.add("python");
            command.add("script.py");
            command.add("arg1");
            command.add("arg2");
            */

            ProcessBuilder pb = new ProcessBuilder(command);
            //File workingDir = new File(workingDirStr);
            //pb.directory(workingDir);
            //pb.inheritIO();<<<<
            Process process = pb.start();

            // Read the output from the Python script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                log.debug("Python Output: " + line);
            }
            
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                log.error("Python Output: " + line);
            }

            // Wait for the process to complete and check the exit code
            return process.waitFor();
        } catch (IOException e) {
            throw new SoUtilException(e);
        } catch (InterruptedException e) {
            throw new SoUtilException(e);
        }
    }
}
