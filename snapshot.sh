
SNAPSHOT_MESSAGE='[SNAPSHOT]'
COMMIT=`git log -1 --pretty=%B | sed '/^$/d'`

if [ $COMMIT = $SNAPSHOT_MESSAGE ]
    then
        git add -A && git commit --amend -m $SNAPSHOT_MESSAGE && git push -f
    else
        git add -A && git commit -m $SNAPSHOT_MESSAGE && git push
fi