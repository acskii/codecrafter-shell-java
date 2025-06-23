public class CommandInvoker {
    private Command _commandToInvoke;

    public void setCommand(Command cmd) {
        _commandToInvoke = cmd;
    }

    public void invoke() {
        _commandToInvoke.execute();
    }
}
