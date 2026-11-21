
package structural.proxy;

interface Document {
    String title();
    String view();
}

// Real Subject: Loads the actual document content from the database.
class RealDocument implements Document {

    private final String content;
    private final String title;

    RealDocument(String id, String title) {
        // Simulates an expensive database read.
        System.out.println("Loading document from database: " + id);

        this.content = "...document content...";
        this.title = title;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public String view() {
        return content;
    }
}

// Virtual Proxy: Delays loading the actual document until it is needed.
class DocumentProxy implements Document {

    private final String id;
    private final String title;
    private RealDocument realDocument;

    DocumentProxy(String id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String title() {
        // Metadata is available without loading the actual document.
        return title;
    }

    @Override
    public String view() {
        // Load the actual document only on the first request.
        if (realDocument == null) {
            realDocument = new RealDocument(id, title);
        }

        return realDocument.view();
    }
}

public class App {

    public static void main(String[] args) {

        Document document =
                new DocumentProxy("DOC-101", "System Design");

        System.out.println("Document title: " + document.title());

        System.out.println("\nViewing document:");
        System.out.println(document.view());

        System.out.println("\nViewing document again:");
        System.out.println(document.view());
    }
}
