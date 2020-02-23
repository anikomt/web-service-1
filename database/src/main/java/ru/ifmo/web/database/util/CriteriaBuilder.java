package ru.ifmo.web.database.util;

import lombok.NoArgsConstructor;


public class CriteriaBuilder {
    StringBuilder builder;

    public CriteriaBuilder select() {
        builder = new StringBuilder("SELECT ");
        return this;
    }

    public CriteriaBuilder selectors(String... fields) {
        for (String field : fields) {
            append(field, ", ");
        }
        builder.setLength(builder.length() - 2);
        return this;
    }

    public CriteriaBuilder from(String tableName) {
        append("FROM");
        append(tableName);
        return this;
    }

    public CriteriaBuilder where(Predicate predicate) {
        append("WHERE").append(predicate);
        return this;
    }

    @Override
    public String toString() {
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            System.out.println(stackTraceElement.toString());
        }
        return builder.toString().concat(";");
    }

    private StringBuilder append(String str) {
        return append(str, " ");
    }

    private StringBuilder append(String str, String delimeter) {
        return builder.append(" ").append(str).append(delimeter);
    }
}
