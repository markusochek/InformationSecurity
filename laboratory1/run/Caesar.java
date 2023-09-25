package laboratory1.run;

public class Caesar {
    private String text;
    private int numberOfFaces;

    Caesar() {
        this.text = "i'm beautiful girl";
        this.numberOfFaces = 6;
    }

    public void main() {
        if ( this.numberOfFaces < 2) {throw new RuntimeException("don't worry, too few faces");}

        StringBuilder codingTape = new StringBuilder(" ".repeat((int) (Math.random() * 100)));
        for (int i = 0; i < this.text.length(); i++) {
            codingTape.append(" ".repeat(this.numberOfFaces))
                    .append(this.text.charAt(i));
        }

        System.out.println(codingTape);
    }
}
