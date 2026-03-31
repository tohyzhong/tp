package cpp.model.contact;

import java.util.List;

/**
 * Tests that a {@code Contact}'s {@code Phone} matches exactly with the given
 * keyword (case-insensitive).
 */
public class ContactPhoneContainsKeywordsPredicate implements ContactSearchPredicate {
    private final List<String> keywords;

    public ContactPhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Contact contact) {
        return this.keywords.stream()
                .anyMatch(keyword -> contact.getPhone().value.equalsIgnoreCase(keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ContactPhoneContainsKeywordsPredicate)) {
            return false;
        }

        ContactPhoneContainsKeywordsPredicate otherPredicate = (ContactPhoneContainsKeywordsPredicate) other;
        return this.keywords.equals(otherPredicate.keywords);
    }
}
