File rpm = new File(localRepositoryPath, "org/codehaus/mojo/rpm/its/rpm-macro-in-release/1.0/rpm-macro-in-release-1.0.rpm")

if (!rpm.exists())
    throw new java.lang.AssertionError("${rpm.getAbsolutePath()} does not exist");

def dist = ["rpm", "--eval", "%{?dist}"].execute()
dist.waitFor()
def distValue = dist.text.toString().trim()

def lines = new File(basedir, "target/rpm/rpm-macro-in-release/SPECS/rpm-macro-in-release.spec").readLines()
[
        "Name: rpm-macro-in-release",
        "Version: 1.0",
        "Release: 123" + distValue
].each {
    if (!lines.contains(it))
        throw new AssertionError("Spec file missing \"${it}\"")
}

return true