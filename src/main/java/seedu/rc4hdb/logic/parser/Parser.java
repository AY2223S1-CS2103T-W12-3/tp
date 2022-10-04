package seedu.rc4hdb.logic.parser;

import seedu.rc4hdb.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser that is able to parse a string into an {@code Object} of type {@code T}.
 */
public interface Parser<T> {

    /**
     * Parses {@code String} into an {@code Object} of type {@code T} and returns it.
     * @throws ParseException if {@code string} does not conform the expected format
     */
    T parse(String string) throws ParseException;
}
