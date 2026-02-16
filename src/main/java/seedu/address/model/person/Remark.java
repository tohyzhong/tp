package seedu.address.model.person;

import java.util.Objects;

/** Represents a Person's remark in the address book. */
public class Remark {
    public final String value;

    /**
     * Constructs a {@code Remark}.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        Objects.requireNonNull(remark);
        this.value = remark;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Remark)) {
            return false;
        }

        Remark otherRemark = (Remark) other;
        return this.value.equals(otherRemark.value);
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

}
