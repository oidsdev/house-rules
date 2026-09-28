#!/usr/bin/env python3
"""Generate HouseRules.xcodeproj/project.pbxproj for the House Rules iOS app."""
import os

ROOT = os.path.expanduser("~/workspace/house-rules-ios")
PROJDIR = os.path.join(ROOT, "HouseRules.xcodeproj")
os.makedirs(PROJDIR, exist_ok=True)

def gid(n):
    # deterministic 24-char hex GUIDs
    return "HR{:022X}".format(n)

# GUIDs
PROJECT = gid(1)
MAIN_GROUP = gid(2)
APP_GROUP = gid(3)
PRODUCTS_GROUP = gid(4)
TARGET = gid(5)
APP_PRODUCT_REF = gid(6)
FRAMEWORKS_PHASE = gid(7)
RESOURCES_PHASE = gid(8)
SOURCES_PHASE = gid(9)
PROJECT_DEBUG = gid(10)
PROJECT_RELEASE = gid(11)
TARGET_DEBUG = gid(12)
TARGET_RELEASE = gid(13)
PROJECT_CONFIG_LIST = gid(14)
TARGET_CONFIG_LIST = gid(15)

sources = [
    ("HouseRulesApp.swift", gid(20)),
    ("Models.swift", gid(21)),
    ("RulesBuilder.swift", gid(22)),
    ("Content.swift", gid(23)),
    ("Views.swift", gid(24)),
]
ASSETS_FILEREF = gid(25)
ASSETS_BUILDFILE = gid(26)
buildfiles = [(gid(30 + i), fr) for i, (_, fr) in enumerate(sources)]

lines = []
def L(s=""):
    lines.append(s)

L("// !$*UTF8*$!")
L("{")
L("\tarchiveVersion = 1;")
L("\tclasses = {")
L("\t};")
L("\tobjectVersion = 77;")
L("\tobjects = {")

L()
L("/* Begin PBXBuildFile section */")
for bf, fr in buildfiles:
    name = [s for s, f in sources if f == fr][0]
    L(f"\t\t{bf} = {{isa = PBXBuildFile; fileRef = {fr} /* {name} */; }};")
L(f"\t\t{ASSETS_BUILDFILE} = {{isa = PBXBuildFile; fileRef = {ASSETS_FILEREF} /* Assets.xcassets */; }};")
L("/* End PBXBuildFile section */")

L()
L("/* Begin PBXFileReference section */")
for name, fr in sources:
    L(f"\t\t{fr} = {{isa = PBXFileReference; lastKnownFileType = sourcecode.swift; path = {name}; sourceTree = \"<group>\"; }};")
L(f"\t\t{ASSETS_FILEREF} = {{isa = PBXFileReference; lastKnownFileType = folder.assetcatalog; path = Assets.xcassets; sourceTree = \"<group>\"; }};")
L(f"\t\t{APP_PRODUCT_REF} = {{isa = PBXFileReference; explicitFileType = wrapper.application; includeInIndex = 0; path = HouseRules.app; sourceTree = BUILT_PRODUCTS_DIR; }};")
L("/* End PBXFileReference section */")

L()
L("/* Begin PBXFrameworksBuildPhase section */")
L(f"\t\t{FRAMEWORKS_PHASE} = {{")
L("\t\t\tisa = PBXFrameworksBuildPhase;")
L("\t\t\tbuildActionMask = 2147483647;")
L("\t\t\tfiles = (")
L("\t\t\t);")
L("\t\t\trunOnlyForDeploymentPostprocessing = 0;")
L("\t\t};")
L("/* End PBXFrameworksBuildPhase section */")

L()
L("/* Begin PBXGroup section */")
L(f"\t\t{MAIN_GROUP} = {{")
L("\t\t\tisa = PBXGroup;")
L("\t\t\tchildren = (")
L(f"\t\t\t\t{APP_GROUP} /* HouseRules */,")
L(f"\t\t\t\t{PRODUCTS_GROUP} /* Products */,")
L("\t\t\t);")
L("\t\t\tsourceTree = \"<group>\";")
L("\t\t};")
L(f"\t\t{APP_GROUP} = {{")
L("\t\t\tisa = PBXGroup;")
L("\t\t\tchildren = (")
for name, fr in sources:
    L(f"\t\t\t\t{fr} /* {name} */,")
L(f"\t\t\t\t{ASSETS_FILEREF} /* Assets.xcassets */,")
L("\t\t\t);")
L("\t\t\tpath = HouseRules;")
L("\t\t\tsourceTree = \"<group>\";")
L("\t\t};")
L(f"\t\t{PRODUCTS_GROUP} = {{")
L("\t\t\tisa = PBXGroup;")
L("\t\t\tchildren = (")
L(f"\t\t\t\t{APP_PRODUCT_REF} /* HouseRules.app */,")
L("\t\t\t);")
L("\t\t\tname = Products;")
L("\t\t\tsourceTree = \"<group>\";")
L("\t\t};")
L("/* End PBXGroup section */")

L()
L("/* Begin PBXNativeTarget section */")
L(f"\t\t{TARGET} = {{")
L("\t\t\tisa = PBXNativeTarget;")
L("\t\t\tbuildConfigurationList = " + TARGET_CONFIG_LIST + " /* Build configuration list for PBXNativeTarget \"HouseRules\" */;")
L("\t\t\tbuildPhases = (")
L(f"\t\t\t\t{FRAMEWORKS_PHASE} /* Frameworks */,")
L(f"\t\t\t\t{RESOURCES_PHASE} /* Resources */,")
L(f"\t\t\t\t{SOURCES_PHASE} /* Sources */,")
L("\t\t\t);")
L("\t\t\tbuildRules = (")
L("\t\t\t);")
L("\t\t\tdependencies = (")
L("\t\t\t);")
L("\t\t\tname = HouseRules;")
L("\t\t\tpackageProductDependencies = (")
L("\t\t\t);")
L(f"\t\t\tproductName = HouseRules;")
L(f"\t\t\tproductReference = {APP_PRODUCT_REF} /* HouseRules.app */;")
L("\t\t\tproductType = \"com.apple.product-type.application\";")
L("\t\t};")
L("/* End PBXNativeTarget section */")

L()
L("/* Begin PBXProject section */")
L(f"\t\t{PROJECT} = {{")
L("\t\t\tisa = PBXProject;")
L("\t\t\tattributes = {")
L("\t\t\t\tBuildIndependentTargetsInParallel = 1;")
L("\t\t\t\tLastUpgradeCheck = 1700;")
L("\t\t\t\tTargetAttributes = {")
L(f"\t\t\t\t\t{TARGET} = {{")
L("\t\t\t\t\t\tCreatedOnToolsVersion = 17.0;")
L("\t\t\t\t\t};")
L("\t\t\t\t};")
L("\t\t\t};")
L("\t\t\tbuildConfigurationList = " + PROJECT_CONFIG_LIST + " /* Build configuration list for PBXProject \"HouseRules\" */;")
L("\t\t\tcompatibilityVersion = \"Xcode 17.0\";")
L("\t\t\tdevelopmentRegion = en;")
L("\t\t\thasScannedForEncodings = 0;")
L("\t\t\tknownRegions = (")
L("\t\t\t\ten,")
L("\t\t\t\tBase,")
L("\t\t\t);")
L(f"\t\t\tmainGroup = {MAIN_GROUP};")
L(f"\t\t\tproductRefGroup = {PRODUCTS_GROUP} /* Products */;")
L("\t\t\tprojectDirPath = \"\";")
L("\t\t\tprojectRoot = \"\";")
L("\t\t\ttargets = (")
L(f"\t\t\t\t{TARGET} /* HouseRules */,")
L("\t\t\t);")
L("\t\t};")
L("/* End PBXProject section */")

L()
L("/* Begin PBXResourcesBuildPhase section */")
L(f"\t\t{RESOURCES_PHASE} = {{")
L("\t\t\tisa = PBXResourcesBuildPhase;")
L("\t\t\tbuildActionMask = 2147483647;")
L("\t\t\tfiles = (")
L(f"\t\t\t\t{ASSETS_BUILDFILE} /* Assets.xcassets in Resources */,")
L("\t\t\t);")
L("\t\t\trunOnlyForDeploymentPostprocessing = 0;")
L("\t\t};")
L("/* End PBXResourcesBuildPhase section */")

L()
L("/* Begin PBXSourcesBuildPhase section */")
L(f"\t\t{SOURCES_PHASE} = {{")
L("\t\t\tisa = PBXSourcesBuildPhase;")
L("\t\t\tbuildActionMask = 2147483647;")
L("\t\t\tfiles = (")
for (bf, _), (name, _) in zip(buildfiles, sources):
    L(f"\t\t\t\t{bf} /* {name} in Sources */,")
L("\t\t\t);")
L("\t\t\trunOnlyForDeploymentPostprocessing = 0;")
L("\t\t};")
L("/* End PBXSourcesBuildPhase section */")

L()
L("/* Begin XCBuildConfiguration section */")

def xcconfig(g, name, settings):
    L(f"\t\t{g} = {{")
    L("\t\t\tisa = XCBuildConfiguration;")
    L("\t\t\tbuildSettings = {")
    for k, v in settings:
        L(f"\t\t\t\t{k} = {v};")
    L("\t\t\t};")
    L(f"\t\t\tname = {name};")
    L("\t\t};")

proj_common = [
    ("ALWAYS_SEARCH_USER_PATHS", "NO"),
    ("CLANG_ANALYZER_NONNULL", "YES"),
    ("CLANG_ANALYZER_NUMBER_OBJECT_CONVERSION", "YES_AGGRESSIVE"),
    ("CLANG_CXX_LANGUAGE_STANDARD", '"gnu++20"'),
    ("CLANG_ENABLE_OBJC_ARC", "YES"),
    ("CLANG_WARN_DOCUMENTATION_COMMENTS", "YES"),
    ("CLANG_WARN_QUOTED_INCLUDE_IN_FRAMEWORK_HEADER", "YES"),
    ("CLANG_WARN_UNGUARDED_AVAILABILITY", "YES_AGGRESSIVE"),
    ("COPY_PHASE_STRIP", "NO"),
    ("DEBUG_INFORMATION_FORMAT", "dwarf-with-dsym"),
    ("ENABLE_NS_ASSERTIONS", "NO"),
    ("ENABLE_STRICT_OBJC_MSGSEND", "YES"),
    ("GCC_C_LANGUAGE_STANDARD", "gnu17"),
    ("GCC_WARN_ABOUT_RETURN_TYPE", "YES_ERROR"),
    ("GCC_WARN_UNINITIALIZED_AUTOS", "YES_AGGRESSIVE"),
    ("MTL_ENABLE_DEBUG_INFO", "NO"),
    ("SWIFT_COMPILATION_MODE", "wholemodule"),
    ("SWIFT_OPTIMIZATION_LEVEL", '"-O"'),
]
xcconfig(PROJECT_DEBUG, "Debug", proj_common + [
    ("COPY_PHASE_STRIP", "NO"),
    ("DEBUG_INFORMATION_FORMAT", "dwarf"),
    ("ENABLE_STRICT_OBJC_MSGSEND", "YES"),
    ("ENABLE_TESTABILITY", "YES"),
    ("ENABLE_USER_SCRIPT_SANDBOXING", "YES"),
    ("GCC_DYNAMIC_NO_PIC", "NO"),
    ("GCC_OPTIMIZATION_LEVEL", "0"),
    ("GCC_PREPROCESSOR_DEFINITIONS", '("DEBUG=1", "$(inherited)")'),
    ("MTL_ENABLE_DEBUG_INFO", "INCLUDE_SOURCE"),
    ("ONLY_ACTIVE_ARCH", "YES"),
    ("SWIFT_ACTIVE_COMPILATION_CONDITIONS", "DEBUG"),
    ("SWIFT_OPTIMIZATION_LEVEL", '"-Onone"'),
])
xcconfig(PROJECT_RELEASE, "Release", proj_common)

target_common = [
    ("ASSETCATALOG_COMPILER_APPICON_NAME", "AppIcon"),
    ("ASSETCATALOG_COMPILER_GLOBAL_ACCENT_COLOR_NAME", "AccentColor"),
    ("CODE_SIGN_STYLE", "Automatic"),
    ("CURRENT_PROJECT_VERSION", "1"),
    ("DEVELOPMENT_TEAM", ""),
    ("ENABLE_PREVIEWS", "YES"),
    ("GENERATE_INFOPLIST_FILE", "YES"),
    ("INFOPLIST_KEY_CFBundleDisplayName", '"House Rules"'),
    ("INFOPLIST_KEY_UIApplicationSupportsIndirectInputEvents", "YES"),
    ("INFOPLIST_KEY_UILaunchScreen_Generation", "YES"),
    ("INFOPLIST_KEY_UISupportedInterfaceOrientations_iPad", '"UIInterfaceOrientationPortrait UIInterfaceOrientationPortraitUpsideDown UIInterfaceOrientationLandscapeLeft UIInterfaceOrientationLandscapeRight"'),
    ("INFOPLIST_KEY_UISupportedInterfaceOrientations_iPhone", '"UIInterfaceOrientationPortrait UIInterfaceOrientationLandscapeLeft UIInterfaceOrientationLandscapeRight"'),
    ("IPHONEOS_DEPLOYMENT_TARGET", "17.0"),
    ("LD_RUNPATH_SEARCH_PATHS", '"$(inherited) @executable_path/Frameworks"'),
    ("MARKETING_VERSION", "1.0"),
    ("PRODUCT_BUNDLE_IDENTIFIER", "com.orbitaldesk.houserules"),
    ("PRODUCT_NAME", '"$(TARGET_NAME)"'),
    ("SDKROOT", "iphoneos"),
    ("SUPPORTED_PLATFORMS", '"iphoneos iphonesimulator"'),
    ("SUPPORTS_MACCATALYST", "NO"),
    ("SUPPORTS_MAC_DESIGNED_FOR_IPHONE_IPAD", "NO"),
    ("SWIFT_EMIT_LOC_STRINGS", "YES"),
    ("SWIFT_VERSION", "5.0"),
    ("TARGETED_DEVICE_FAMILY", '"1,2"'),
]
xcconfig(TARGET_DEBUG, "Debug", target_common)
xcconfig(TARGET_RELEASE, "Release", target_common)
L("/* End XCBuildConfiguration section */")

L()
L("/* Begin XCConfigurationList section */")
L(f"\t\t{PROJECT_CONFIG_LIST} = {{")
L("\t\t\tisa = XCConfigurationList;")
L("\t\t\tbuildConfigurations = (")
L(f"\t\t\t\t{PROJECT_DEBUG} /* Debug */,")
L(f"\t\t\t\t{PROJECT_RELEASE} /* Release */,")
L("\t\t\t);")
L("\t\t\tdefaultConfigurationIsVisible = 0;")
L("\t\t\tdefaultConfigurationName = Release;")
L("\t\t};")
L(f"\t\t{TARGET_CONFIG_LIST} = {{")
L("\t\t\tisa = XCConfigurationList;")
L("\t\t\tbuildConfigurations = (")
L(f"\t\t\t\t{TARGET_DEBUG} /* Debug */,")
L(f"\t\t\t\t{TARGET_RELEASE} /* Release */,")
L("\t\t\t);")
L("\t\t\tdefaultConfigurationIsVisible = 0;")
L("\t\t\tdefaultConfigurationName = Release;")
L("\t\t};")
L("/* End XCConfigurationList section */")

L("\t};")
L(f"\trootObject = {PROJECT} /* Project object */;")
L("}")

with open(os.path.join(PROJDIR, "project.pbxproj"), "w") as f:
    f.write("\n".join(lines) + "\n")
print("wrote", os.path.join(PROJDIR, "project.pbxproj"))
