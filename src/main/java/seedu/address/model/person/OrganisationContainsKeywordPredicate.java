package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Organisation} matches the keyword.
 * Matching is case-sensitive.
 */
public class OrganisationContainsKeywordPredicate implements Predicate<Person> {

    private final String keyword;

    /**
     * Constructs a predicate using the given keyword.
     *
     * @param keyword The organisation keyword to match against.
     */
    public OrganisationContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        return person.getOrganisation() != null
            && person.getOrganisation().toString().equals(keyword); // case-sensitive exact match
    }

    @Override
    public boolean equals(Object other) {
        return other == this // same object
            || (other instanceof OrganisationContainsKeywordPredicate
            && keyword.equals(((OrganisationContainsKeywordPredicate) other).keyword));
    }

    @Override
    public String toString() {
        return "OrganisationContainsKeywordPredicate{keyword=" + keyword + "}";
    }
}
