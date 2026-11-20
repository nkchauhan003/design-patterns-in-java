package creational.prototype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Demonstrates the Prototype Design Pattern using a Registry.
 */
public class App {

    public static void main(String[] args) {

        // Create a report template.
        Report template = new Report();
        template.setTitle("Annual Report");
        template.setSections(List.of("Introduction", "Financials", "Conclusion"));
        template.setStyle(new Style("Arial", "company-logo.png"));

        // Register the prototype.
        Registry registry = new Registry();
        registry.add("annual", template);

        // Create new reports by cloning the registered prototype.
        Report report1 = registry.get("annual");
        Report report2 = registry.get("annual");

        // Modify one cloned report.
        report1.setTitle("Annual Report 2026");
        report1.getSections().add("Future Outlook");

        System.out.println(report1.getTitle());
        System.out.println(report1.getSections());

        // Original template and second clone remain unchanged.
        System.out.println(template.getTitle());
        System.out.println(template.getSections());

        System.out.println(report2.getTitle());
        System.out.println(report2.getSections());
    }
}

/**
 * Prototype class representing a report.
 */
class Report {

    private String title;
    private List<String> sections;
    private Style style;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getSections() {
        return sections;
    }

    public void setSections(List<String> sections) {
        this.sections = sections;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    /**
     * Creates an independent copy of the current Report.
     * <p>
     * Copies the mutable sections list while safely sharing
     * the immutable Style record.
     *
     * @return a cloned Report instance
     */
    public Report copy() {

        Report clone = new Report();

        clone.title = this.title;
        clone.sections = this.sections == null
                ? null
                : new ArrayList<>(this.sections);
        clone.style = this.style;

        return clone;
    }
}

/**
 * Immutable value object representing report styling.
 */
record Style(String font, String logo) {
}

/**
 * Registry maintains predefined prototypes and creates copies on demand.
 */
class Registry {

    private final Map<String, Report> templates = new HashMap<>();

    /**
     * Registers a report prototype.
     *
     * @param name   prototype identifier
     * @param report report template
     */
    public void add(String name, Report report) {
        templates.put(name, report);
    }

    /**
     * Returns a new copy of the registered prototype.
     *
     * @param name prototype identifier
     * @return cloned report, or null if the prototype does not exist
     */
    public Report get(String name) {

        Report report = templates.get(name);

        if (report == null) {
            return null;
        }

        return report.copy();
    }
}