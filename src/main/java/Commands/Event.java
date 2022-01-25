package Commands;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private String at;
    private LocalDate date;
    private LocalTime time;
    private String dateFormat;
    private String timeFormat;

    private static final String[] dateFormats = {
            "dd/MM/yyyy",
            "dd MM yyyy",
            "yyyy/MM/dd",
            "yyyy MM dd",
            "MMM dd yyyy",
    };
    private static final String[] timeFormats = {
            "HHmm",
            "HH:mm",
    };

    public Event(String description, String at) {
        super(description, "E");
        this.at = at;
    }

    public boolean isValidTime() {
        for (String format : timeFormats) {
            try {
                if (this.at.split(" ").length != 1) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                    LocalTime.parse(this.at.split(" ")[1], formatter);
                    this.timeFormat = format;
                    return true;
                } else {
                    return false;
                }
            } catch (DateTimeParseException e) {
            }
        }
        return false;
    }

    public boolean isValidDate() {
        String d = this.at.split(" ")[0].replace("-", " ");
        for (String format : dateFormats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                LocalDate.parse(d, formatter);
                this.dateFormat = format;
                return true;
            } catch (DateTimeParseException e) {
            }
        }
        return false;
    }
    @Override
    public String getDate() {
        String d = this.at.split(" ")[0].replace("-", " ");
        if (this.isValidDate()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(this.dateFormat);
            this.date = LocalDate.parse(d, formatter);
            return this.date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        } else {
            return "";
        }
    }
    @Override
    public String getTime() {
        if (this.isValidTime()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(this.timeFormat);
            this.time = LocalTime.parse(this.at.split(" ")[1], formatter);
            return this.time.format(DateTimeFormatter.ofPattern("HH:mm a"));
        } else {
            return "";
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + (this.getDate() + " " + this.getTime()).trim() + ")";
    }
}