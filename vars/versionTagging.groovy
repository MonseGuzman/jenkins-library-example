def call() {
	sh '''
		ls 
		ls scripts
		echo "##########[debug] imprime variables"
	'''
	sh '''
		git fetch --tags --depth=1000 --prune
        autotag; git push origin --tags
	'''
    // if [`git rev-parse --abbrev-ref HEAD` != "master" ]
    // then
    //     git branch --track master origin/master
    // fi
    // git-chglog -next-tag `autotag -n` -o CHANGELOG.md
}