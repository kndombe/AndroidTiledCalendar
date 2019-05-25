# android-tiled-calendar

This library offers a calendar view for Android development that allows to customize each month cell by adding a desired number of tiles displaying a specified text.

Min sdk: 21

# How to add the library as dependency
In your root, project level `build.gradle` file add

    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
If you already have `allprojects > repositories` in that file, simply add `maven { url 'https://jipack.io' }` at the end of the `repositories` body.

Then in your module level `build.gradle`, add this in the `dependencies`

    implementation 'com.github.kndombe:android-tiled-calendar:1.0.0
    
Then sync. You can also do that through `File > Sync Project with Gradle Files`.

# How to use the library
Using this calendar doesn't differ much from the way you would use the regurlar Android `CalendarView`.

## Initialize calendar
In the layout file that should host the calendar view, use the `<com.tiledcalendar.tiledmonthview.TiledMonthView>` selector as you would any other view.
Example:

    <com.tiledcalendar.tiledmonthview.TiledMonthView
        android:id="@+id/tiled_month_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

In your code activity, you can retrieve the view using its ID with either one of the following:

    TiledMonthCalendar calendar = findViewById(R.id.tiled_month_view);

    // or

    TiledMonthView calendar = findViewById(R.id.tiled_month_view);

## Add entries
You can add entries that you want to see displayed as tiles in the calendar.

Before adding entries to the calendar, you need to create them.

You can create entries by using the `Entry` interface and the `EntryFactory` like this.

    Entry entry = EntryFactory.makeEntry(
        "asdf123" /* uniqueID */,
        "Coffe attack" /* name */,
        start /* startDateTime as a Calendar instance*/,
        end /* endDateTime as a Calendar instance */,
        Color.BLUE /* color */);

You can then pass the entries you've created to the calendar like so:

    calendar.addEntry(entry); // adding one entry

    // or

    calendar.addEntries(list_of_entries); // adding a List of entries

The calendar will accept objects from any class that implements the `Entry` interface defined in `com.tiledcalendar.entries`.That means that you could use your own custom class to create new instances of entries from, instead of using the `EntryFactory.makeEntry`. You would only need to make sure that your class implements the `Entry` interface and overrides all necessary methods from it. You will then be able to pass those entries created from your custom class directly to the calendar in the same exact way as above (if your class implements the `Entry` interface, the calendar should have no problem understanding). The methods you'll need to override are:

* `getUniqueID`: the calendar will call this method on each entry to get their unique identifier in order to correctly tell them apart when displaying their tiles. The calendar also uses this ID to tell you which entry has been targetted when the calendar registers a click on a tile.
* `getName`: the calendar will call this method on each entry to get their name. This `String` value will be used as text for the tile corresponding to each entry.
* `getStartDateTime`: the calendar will call this method together with `getEndDateTime` on each entry to determine where on the calendar the tile should appear. If this entry expands through more than one day, there will be more than one tile and `getStartDateTime` tells the calendar where to start displaying those tiles.
* `getEndDateTime`: the calendar will call this method together with `getStartDateTime` on each entry to determine where on the calendar the tile should appear. If this entry expands through more than one day, there will be more than one tile and `getEndDateTime` tells the calendar where to stop displaying those tiles.
* `getColor`: the calendar will call this method on each entry to determine what color their tiles should have when they are displayed.


# Listen to events
Use the `setTiledMonthEventListener` method to set an `OnTiledMonthEventListener` that will be notified when actions are performed within the calendar.

The listener is notified for the following events:

- `onCellClick`: when a cell in the calendar view has been clicked. The notification comes with some parameters:
  - `date`: the date corresponding to the clicked cell;
  - `tileIDs`: a list of IDs corresponding to the entries displayed in the selected cell;
  - `selectedDateChanged`: a boolean that indicates whether the clicked cell corresponds to a date different from what was the `selectedDate` before this event;
  - `monthChanged`: a boolean that indicates whether the currently displayed month has changed as a result of this event. This happens if the clicked cell corresponds to a date that is not in the currently displayed month (day 1 through 28/29/30/31 according to the month; the adjacent months are not considered to be "currently displayed");
- `onSwipe`: when the calendar has been swiped right or left. The notification comes with some parameters:
  - `monthChanged`: a boolean indicating whether or not the currently selected month has changed in result to this action. It will be the case if the current month has been swiped more than 50% to the right or to the left;
  - `date`: the currently selected date after this action. It's the same one as before if the month has not changed;
- `onTileClicked`: when a tile has been clicked inside of a date cell dialog. The notification comes with some parameters:
  - `date`: the date corresponding to the cell containing the clicked tile;
  - `id`: the id of the entry corresponding to the clicked tile;
- `onMonthChanged`: when the currently displayed month has been changed. This can happen after a swipe, a click on a cell from an adjacent month or from using the previous/next button. The notification comes with one parameter:
  - `date`: the currently selected date after this action;

# How to contribute to this repository
- [x] Fork and clone repository
- [x] Make the changes you're interested in
- [x] You can test the library by installing it as an application (on a phone or emulartor) from within this same project
  - [x] In the `build.gradle` file of the library, uncomment the line `applicationId "com.tiledcalendar"` (if the comment is not there, add `application "com.tiledcalendar"` under `defaultConfig`)
  - [x] At the top of the `build.gradle` file, you'll see `apply plugin: 'com.android.library`. Replace `library` with `application`
  - [x] Click on "Sync". You can also do that through `File > Sync Project with Gradle Files`.
  - [x] You can now install this project on a device or emulator as an application.
  - [x] There is a `TiledMonthTesterActivity` under `com.tiledcalendar` that you can use to run any simulations. Feel free to create your own Activities.
- [x] If you turned the library into an application for testing, make sure to revert that
  - [x] In the `build.gradle` file, make sure to remove the `applicationId` under `defaultConfig` (you can just comment that line out).
  - [x] At the top of the `build.gradle` file, make sure you have `apply plugin: 'com.android.library'` (and not `com.android.application`)
- [x] Submit a pull request explaining the problem and solution.
