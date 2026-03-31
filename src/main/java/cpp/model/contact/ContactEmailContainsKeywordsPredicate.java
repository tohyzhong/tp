package cpp.model.contact;

import java.util.List;

/**
 * Tests that a {@code Contact}'s {@code Email} matches exactly with the given
 * keyword (case-insensitive).
 */
public class ContactEmailContainsKeywordsPredicate implements ContactSearchPredicate {
    private final List<String> keywords;

    public ContactEmailContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Contact contact) {
        return this.keywords.stream()
                .anyMatch(keyword -> contact.getEmail().value.equalsIgnoreCase(keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ContactEmailContainsKeywordsPredicate)) {
            return false;
        }

        ContactEmailContainsKeywordsPredicate otherPredicate = (ContactEmailContainsKeywordsPredicate) other;
        return this.keywords.equals(otherPredicate.keywords);
    }
}
