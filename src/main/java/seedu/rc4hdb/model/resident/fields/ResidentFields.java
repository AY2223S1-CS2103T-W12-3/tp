package seedu.rc4hdb.model.resident.fields;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Stores the identifiers for the resident fields.
 */
public class ResidentFields {
    public static final String EMAIL = "Email";
    public static final String INDEX = "Index";
    public static final String GENDER = "Gender";
    public static final String HOUSE = "House";
    public static final String MATRIC = "Matric";
    public static final String NAME = "Name";
    public static final String PHONE = "Phone";
    public static final String ROOM = "Room";
    public static final String TAG = "Tags";

    public static final List<String> FIELDS = List.of(EMAIL, INDEX, GENDER, HOUSE, MATRIC, NAME, PHONE, ROOM, TAG);
    public static final List<String> LOWERCASE_FIELDS = FIELDS.stream()
            .map(String::toLowerCase)
            .collect(Collectors.toList());
}
