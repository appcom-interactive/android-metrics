# integration test module

## gradle tasks
- connectedCheck
- detekt
- ktlintCheck
- lint
- test
- dependecyUpdates
-

## flavor specific
FLAVOR = DevelopAppcenterDebug
- count{FLAVOR}DexMethods
- jacocoTest{FLAVOR}UnitTestReport
- test{FLAVOR}UnitTest
- connected{FLAVOR}AndroidTest
- ktlint{FLAVOR}Check