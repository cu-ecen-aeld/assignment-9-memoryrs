# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-memoryrs.git;protocol=ssh;branch=main \
           file://aesdchar-init"

PV = "1.0+git${SRCPV}"
SRCREV = "91255843df4066472228e847a8c8f95b327df9d9"

S = "${WORKDIR}/git/aesd-char-driver"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/aesd-char-driver"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdchar-init"

FILES:${PN} += "${sysconfdir}/*"
FILES:${PN} += "${base_bindir}/aesdchar_load"
FILES:${PN} += "${base_bindir}/aesdchar_unload"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

KERNEL_VERSION = "5.15.184-yocto-standard"

do_install () {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/aesdchar-init ${D}${sysconfdir}/init.d/

    install -d ${D}${base_bindir}
    install -m 0755 ${S}/aesdchar_load ${D}${base_bindir}/
	install -m 0755 ${S}/aesdchar_unload ${D}${base_bindir}/

    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}
    install -m 0755 ${S}/aesdchar.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/
}
