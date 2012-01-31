SECTION = "kernel"
DESCRIPTION = "Linux kernel for TI33x EVM from PSP, based on am335x-kernel"
LICENSE = "GPLv2"
KERNEL_IMAGETYPE = "uImage"

require multi-kernel.inc

S = "${WORKDIR}/git"

MULTI_CONFIG_BASE_SUFFIX = ""

BRANCH = "v3.2-staging"
SRCREV = "83d907e1b05dabc44f3bb64532d7b58d059a14c0"
MACHINE_KERNEL_PR_append = "a+gitr${SRCREV}"

COMPATIBLE_MACHINE = "(ti33x)"

THISDIR := "${@os.path.dirname(bb.data.getVar('FILE', d, True))}"
CONFIGS_PSP = "${@base_set_filespath(["${THISDIR}/${PN}-${PV}/tipspkernel"], d)}:\
${@base_set_filespath(["${THISDIR}/${PN}/tipspkernel"], d)}:\
${@base_set_filespath(["${THISDIR}/files/tipspkernel"], d)}:"
FILESPATH =. "${@base_contains('DISTRO_FEATURES', 'tipspkernel', "${CONFIGS_PSP}", "", d)}"

SRC_URI += "git://arago-project.org/git/projects/linux-am33x.git;protocol=http;branch=${BRANCH} \
	file://defconfig"

PATCHES_OVER_PSP = " \
	file://0001-f_rndis-HACK-around-undefined-variables.patch \
	file://0002-da8xx-fb-add-DVI-support-for-beaglebone.patch \
	file://0003-arm-omap-mux33xx-Add-i2c2-pin-mux.patch \
	file://0004-omap-hwmod-33xx-Add-support-for-third-i2c-bus.patch \
	file://0005-beaglebone-rebase-everything-onto-3.2-WARNING-MEGAPA.patch \
	file://0006-more-beaglebone-merges.patch \
	file://0007-beaglebone-disable-tsadc.patch \
	file://0008-tscadc-Add-general-purpose-mode-untested-with-touchs.patch \
	file://0009-tscadc-Add-board-file-mfd-support-fix-warning.patch \
	file://0010-AM335X-init-tsc-bone-style-for-new-boards.patch \
	file://0011-tscadc-make-stepconfig-channel-configurable.patch \
	file://0012-tscadc-Trigger-through-sysfs.patch \
	file://0013-meta-ti-Remove-debug-messages-for-meta-ti.patch \
	file://0014-tscadc-switch-to-polling-instead-of-interrupts.patch \
	file://0015-beaglebone-fix-ADC-init.patch \
"

SRC_URI += "${@base_contains('DISTRO_FEATURES', 'tipspkernel', "", "${PATCHES_OVER_PSP}", d)}"
SRC_URI_append_beaglebone = " file://logo_linux_clut224.ppm"