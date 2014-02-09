App = Ember.Application.create({
    currentPath: '',
    tags: [],
    session: null,
    user: null,
    isLoggedIn: false
});


App.deferReadiness();

var session = localStorage.getItem('session');
if (session != null) {

    var userProperties = JSON.parse(localStorage.getItem('user'));
    var userObject = Ember.Object.create();
    userObject.setProperties(userProperties);

    App.set('user', userObject);
    App.set('session', session);
    App.set('isLoggedIn', true);

    Ember.$.ajaxSetup({
        headers: {
            'X-Auth': session
        }
    });
}

App.advanceReadiness();