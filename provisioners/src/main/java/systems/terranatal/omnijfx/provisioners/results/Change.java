package systems.terranatal.omnijfx.provisioners.results;

public interface Change {

  record DirectoryCreated(String directory) implements Change {}
}
