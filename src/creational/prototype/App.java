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

        // Create and configure the original prototype.
        Report template = new Report();
        template.setTitle("Annual Report");
        template.setSections(
                List.of("Introduction", "Financials", "Conclusion"));
        template.setStyle(new Style("Arial", "company-logo.png"));

        // Register the prototype.
        Registry registry = new Registry();
        registry.add("annual", template);

        // Create independent copies of the registered prototype.
        Report report1 = registry.get("annual");
        Report report2 = registry.get("annual");

        // Modify the cloned reports independently.
        report1.setTitle("Annual Report Jan 2026");
        report1.getSections().add("January Overview");

        report2.setTitle("Annual Report Feb 2026");
        report2.getSections().add("February Overview");

        // Display cloned reports.
        System.out.println(report1.getTitle());
        System.out.println(report1.getSections());

        System.out.println(report2.getTitle());
        System.out.println(report2.getSections());

        // Verify independent object creation.
        System.out.println(report1 != report2);

        // Verify independent Style objects.
        System.out.println(report1.getStyle() != report2.getStyle());

        // Verify original prototype remains unchanged.
        System.out.println(template.getTitle());
        System.out.println(template.getSections());
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
     *
     * Copies all fields, including mutable nested objects,
     * to ensure the cloned object is independent of the original.
     *
     * @return a deep copy of the Report
     */
    public Report copy() {

        Report clone = new Report();

        // Strings are immutable and can safely be shared.
        clone.title = this.title;

        // Create an independent copy of the mutable list.
        clone.sections = this.sections == null
                ? null
                : new ArrayList<>(this.sections);

        // Create an independent Style object.
        clone.style = this.style == null
                ? null
                : new Style(this.style.font(), this.style.logo());

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
     * Registers a copy of the supplied prototype.
     *
     * @param name   prototype identifier
     * @param report report prototype
     */
    public void add(String name, Report report) {
        templates.put(name, report.copy());
    }

    /**
     * Returns an independent copy of the registered prototype.
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