ICal to Calendar App
====================

For some reason, Android/native calendar/Google Calendar currently don't open `.ics` (iCalendar) event files.
However, it is common for websites to distribute "Add event to my calendar" links via these files. So this is
a simple parser that reads `.ics` files and launches an [Insert Intent for Calendar 
Provider][http://developer.android.com/guide/topics/providers/calendar-provider.html#intents]
so that any calendar app can get them. 

As with any program related to times and dates, I'm sure there are some weird edge cases I haven't considered.
Also I don't have a lot of file examples. So if you use this and a calendar link doesn't show up right, send
me the file and I will see what I can do.

Curently, the app only supports `VEVENT`s and only one event per file. This will be fixed in the future.






MIT LICENCE
-----------

Copyright (C) 2014 Charles Julian Knight

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the 
Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
