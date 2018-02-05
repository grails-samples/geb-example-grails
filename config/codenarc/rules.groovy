ruleset {
    description 'Grails-CodeNarc Project RuleSet'

    ruleset('rulesets/basic.xml')
    ruleset('rulesets/braces.xml')
    ruleset('rulesets/convention.xml') {
        'NoDef' { enabled = false }
        'MethodReturnTypeRequired' { enabled = false }
        'FieldTypeRequired' { enabled = false }
        'MethodParameterTypeRequired' { enabled = false }
        'NoTabCharacter' { enabled = false }
    }
    ruleset('rulesets/design.xml') {
        'Instanceof' { enabled = false }
    }
    ruleset('rulesets/dry.xml') {
        'DuplicateStringLiteral' { enabled = false }
        'DuplicateNumberLiteral' { enabled = false }
        'DuplicateMapLiteral' { enabled = false }
        'DuplicateListLiteral' { enabled = false }
    }
    ruleset('rulesets/exceptions.xml')
    ruleset('rulesets/formatting.xml') {
        'SpaceAroundMapEntryColon' { enabled = false }
        'ClassJavadoc' { enabled = false }
        'Indentation' { enabled = false }
        'LineLength' { enabled = false }
    }
    ruleset('rulesets/generic.xml')
    ruleset('rulesets/imports.xml')
    ruleset('rulesets/naming.xml') {
        'MethodName' { enabled = false }
    }
    ruleset('rulesets/unnecessary.xml')
    ruleset('rulesets/unused.xml')
    ruleset('rulesets/grails.xml') {
        'GrailsMassAssignment' { enabled = false }
    }
}