package systems.terranatal.omnijfx.provisioners;

@FunctionalInterface
public interface ProvisionCheck {
  enum Command {
    PROCEED, ABORT, SKIP, UNRESOLVED;
  }

  ProvisioningCheckCommand check();

  sealed interface ProvisioningCheckCommand extends ProvisionCheck {}

   record ProceedToNextCheck(ProvisionCheck next) implements ProvisioningCheckCommand {
     @Override
     public ProvisioningCheckCommand check() {
       return next.check();
     }
   }

   record SkipProvisioning(String reason) implements ProvisioningCheckCommand {
    public SkipProvisioning() {
      this("Unknown Reason");
    }
     @Override
     public ProvisioningCheckCommand check() {
       return new End(Command.SKIP, reason);
     }
   }

   record AbortProvisioning(String reason) implements ProvisioningCheckCommand {
     public AbortProvisioning() {
       this("Unknown Reason");
     }
     @Override
     public ProvisioningCheckCommand check() {
       return new End(Command.ABORT, reason);
     }
   }

   record End(Command finalCommand, String reason) implements ProvisioningCheckCommand {

     @Override
     public ProvisioningCheckCommand check() {
       return this;
     }
   }

   static ProvisioningCheckCommand executeNext(ProceedToNextCheck current) {
     var next = current.check();

     if (next instanceof ProceedToNextCheck n) {
       return executeNext(n);
     }
     return next;
   }

   static End executeChain(ProceedToNextCheck first) {
     var last = executeNext(first);
     var end = last.check();

     if (end instanceof End e) {
       return e;
     }

     return new End(Command.UNRESOLVED, "The validation could not be resolved");
   }
}
