Feature: Beneficiary

  Scenario: First
    Given a default android phone
    Given Revolut app is started on the phone
    Given click button skip
    Given type "662266" to phone number
    Given click button next
    Given set password 1111
    Given click button not now
    Given click button transfer
    Given click button to bank account
    Given click button skip
    Given click button add new beneficiary
    Given click button to myself
    Given click button next
    Given click button next
    Given type "FR1420041010050500013M02606" to IBAN
    Given type "AMFCFRPP" to BIC/SWIFT
    Given click button next
    When click button next
    Then message "Beneficiary Errrroijjjjnbbb Gvvvvh was successfully created" is present