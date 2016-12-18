#! /bin/sh

cmd="run"

if [ $# == 1 ]; then
	cmd=$1
fi

#echo $cmd
activator=activator

if [ "run" == "$cmd" ]; then

	# run for debug
	${activator} run -Dhttp.port=9000 -Dactivator.checkForUpdates=false -Dactivator.checkForTemplateUpdates=false

elif [ "release" == "$cmd" ]; then

	# release
	${activator} dist -Dhttp.port=9000 -Dactivator.checkForUpdates=false -Dactivator.checkForTemplateUpdates=false -Dplay.crypto.secret=loginner123

else
	echo "Unknow '$cmd' command."
fi

