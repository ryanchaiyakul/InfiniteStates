
# WORK FLOW

## 1. Create/Initialize workspace

go to your workspace, let's say ~/workspace
```
 $ cd ~/workspace
```

Clone the Repo to workspace :
```
 $ git clone https://github.com/ryanchaiyakul/InFiniteStates.git
```

code will be checkout to InFiniteStates sub-directory
```
 $ cd InFiniteStates
```

Make sure the local master is up-to-date:
```
 $ git pull origin main
```

Create new branch:
```
 $ git branch <branch_name>
```

Move to branch:
```
 $ git checkout <branch_name>
```

## 2. Start working

Navigate file structure as needed:
```
 $ ls
 $ cd folder_name
```

start edit your stuff ...................

## 3) validate your edits

go to your InFiniteStates workspace
```
 $ cd ~/workspace/InFiniteStates
```

validate and test
```
 $ ./gradlew build
 $ ./gradlew test
```
      
if test fail, fix your code

## 4) if test pass and ready to commit 

Verify status
```
 $ git status
```

if you created new file you need to add the files to the branch:
```
 $ git add .
```

Commit the files:
```
 $ git commit -a
```

add comment

Add branch and files to the Remote Repo:
```
 $ git push -u origin <branch_name>
```

## 5. request code to be add/merge (ie. Pull request)
Go to the github website to manage pull request and merge.

## 6. when pull request completed the code will be merged.  clean up

Switch back to local master so you can delete the local branch:
```
 $ git checkout main
```

Delete local branch:
```
 $ git branch -d branch_name
```

## 7. :+1: congratulations, your edit have been accepted to the main repository.  If you have more edition, changes, code fix, start the process from (1) again.

