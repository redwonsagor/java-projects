package drawingTool;

public abstract class AntDecorator implements AntComponent {
   
    protected final AntComponent inner;

    public AntDecorator(AntComponent inner) {
        this.inner = inner;
    }


    public AntComponent getAntComponent() {
        return inner;
    }


}
