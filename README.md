# Android Academy 2022


## 1. Project Setup

### 1.1 Set system environment variables

TMDB_API_KEY=
TMDB_USER_NAME=
TMDB_PASSWORD=

On Mac (with ZSH)
- edit `~/.zshenv`
- apply by `exec zsh`
- verify by `echo $TMDB_API_KEY`

### 1.2 In app directory add local files
It is not good practice to have such files as a part of the repository (even private).
Thus those files are excluded in .gitignore and will be available locally only.

#### 1.2.1. gradle.properties 
With keystore credentials (use the same password for keystore and key):
KEYSTORE_PASSWORD=WriteYourKeystorePassword
KEY_PASSWORD=WriteYourKeystorePassword
ALIAS=android-academy-2022

#### 1.2.2 android-academy-2022.keystore 
Generate file from menu
Build -> Generate Signed Bundle -> App Bundle



