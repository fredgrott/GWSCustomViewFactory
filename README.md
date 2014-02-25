![gws_logo](https://github.com/shareme/GWSCustomViewFactory/raw/master/readme_images/grottworkshop_logo.png)

GWSCustomViewFactory
---

An Android Project Library that shows a Factory pattern to reigster custom Views so that we
get a slight speed incease in the  UI layout inflation process. Its nt really a full library, it just shows the
view factory pattern which you typically use when coding customized views and widgets often is used to
allow you to only specify the view name instead of the fullpath name in XML for the full appplication UI.

# Limitations

Its not a full android project library. Its just a quick hack to show the pattern,

Two, be carefull not to pass a null view, use the newInstance pattern to grab an application context
so that although we have a null view before setup we can still register the customized view and
do UI layout inflation before the view is non-null.
