# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-memoryrs.git;protocol=ssh;branch=main \
           file://misc-modules-init"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "a5a0c66d16058c8c7fac7c3c05cc96e90387ff0b"

S = "${WORKDIR}/git/misc-modules"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "misc-modules-init"

FILES:${PN} += "${sysconfdir}/*"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

KERNEL_VERSION = "5.15.184-yocto-standard"

do_install () {
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}
	install -m 0755 ${S}/hello.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/
	install -m 0755 ${S}/faulty.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/
	install -m 0755 ${WORKDIR}/misc-modules-init ${D}${sysconfdir}/init.d/
}
