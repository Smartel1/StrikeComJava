[![Build Status](https://travis-ci.org/Smartel1/StrikeComJava.svg?branch=master)](https://travis-ci.org/Smartel1/StrikeComJava)
# Zabastcom backend
 Copy of github.com/smartel1/strikecom, but stands on the top of java+spring stack


## What is Zabastcom?
 Zabastcom is nonprofit project which aggregates news in the field of labor conflicts

## Domain entities of application:
### Conflict
 One of the main entities.
 Combines events into chains. Conflicts can branch by attaching to events of other conflicts.
 The database uses a nested set to bind to a specific parent event.
 API users cannot enter conflicting data (e.g. parent_event is not included in the list of parent conflict events)
 because they don’t have the ability to change parent_id.
 A nested set is used for ease of querying.
### Event
 One of the main entities.
 Belongs to a conflict or multiple conflicts if it is a branch point
 May be created by authenticated users. May be published by moderators only
### News
 One of the main entities.
 Doesn't belong to conflicts, however similar to the event
### Photo (URL, not a file)
 May be attached to event or news
### Video (URL, preview URL and type)
 May be attached to event or news
### Client version
 Service entity for mobile clients. Helps to control updates necessity
### References
 Clients may load references or request for hash (which tell whether references have been changed)