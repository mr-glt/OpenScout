#OpenScout

**OpenScout** is an Android app that provides an end to end scouting solution for teams competing in the *FIRST* Robotics competition. It leverages the Free-Tier of Google Firebase to provide a real time connection between scout and drive teams, while providing an easy-to-deploy database so that any team can run their own.
<br>
<img src="https://raw.githubusercontent.com/mr-glt/OpenScout/master/Art/Screenshots/login.png" width="275"/>
<img src="https://raw.githubusercontent.com/mr-glt/OpenScout/master/Art/Screenshots/driver.png" width="275"/>
<img src="https://raw.githubusercontent.com/mr-glt/OpenScout/master/Art/Screenshots/team.png" width="275"/>
## Disclaimer
*This app is currently under active development. The framework of the app is implemented, however, data related to the 2017 game will be implemented after kick-off and throughout the season.*

##How to Clone, Build and Deploy
*A better guide will be released closer to the end of build season*
###Android App
1. Download and install <a target="blank" href="https://developer.android.com/studio/index.html">Android Studio</a>
2. Clone repository.
3. Open the root of the repository in Android Studio.
###Firebase
1. Create a Google Account.
2. Visit your <a target="blank" href="https://console.firebase.google.com/?pli=1">Firebase Console</a>.
3. Create a new project and call it whatever you would like.
4. Click on "Add Firebase to your Android app"
5. Use **Package Name** `xyz.syzygylabs.openscout` and follow <a target="blank" href="https://developers.google.com/android/guides/client-auth">this</a> to get the debug certificate.
6. Copy `google-services.json` to `/app/`. You do **not** need to add the dependencies to the `build.gradle`. Hit finish to close the dialogue.
7. Navigate the *Authentication* tab in the console.
8. Under *SIGN-IN METHOD* enable `Email/Password` and `Google`. *By default, the Firebase will require a user to be authenticated to read and write to the database.*
9. At this point, your database and app should be ready for use.

##Contributing
Feel free to open a pull request with any changes or improvements. If you find a bug, report it in the Issues tab. For any other requests feel free to email me at *colson72098@gmail.com*.
