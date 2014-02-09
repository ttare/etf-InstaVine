
App.Router.map(function() {

    this.route('register');

    this.route('newest');
    this.route('top');
    this.resource('tags', {path: 'tags/:tag'});

    this.resource('user', function() {
        this.route('videos');
        this.route('settings');
    });

    this.resource('admin', function() {
        this.route('users');
        this.route('groups');
        this.route('access');
        this.route('contents');
    });

    this.route('test');
});


App.ApplicationRoute = Ember.Route.extend({
    model: function() {
        Ember.$.getJSON('api/v1/tags', function(response) {
            Ember.set('App.tags', response);
        });
        return new Ember.Object();
    },
    actions: {
        do_modal: function(name, controller) {
            var controller = controller || 'application';
            this.render(name, { into: 'application', outlet: 'modal', view: 'modal', controller: controller });
        },
        close: function() {
            this.disconnectOutlet({outlet: 'modal', parentView: 'application'});
        },
        go_back: function() {
            history.back();
        },
        logout: function() {
            App.set('user', null);
            App.set('session', null);
            App.set('isLoggedIn', false);
            localStorage.removeItem('user');
            localStorage.removeItem('session');

            this.transitionTo('index');
        },
        login: function() {
            var data = {};

            data.username = document.getElementById('username').value;
            data.password = document.getElementById('password').value;

            var that = this;
            var appController = this.controllerFor('application');

            Ember.$.post("api/v1/login", data, function(response) {
                if (response.auth == null) 
                {
                    // Login error
                    appController.set('error', 'Wrong username and/or password!');
                } else {
                    // Login success
                    appController.set('error', '');

                    var xAuthHeader = response.user.username + ':' + response.auth;

                    App.set('user', response.user);
                    App.set('session', xAuthHeader);
                    App.set('isLoggedIn', true);

                    localStorage.setItem('user', JSON.stringify(response.user));
                    localStorage.setItem('session', xAuthHeader);

                    Ember.$.ajaxSetup({
                        headers: {
                            'X-Auth': xAuthHeader
                        }
                    });

                    that.send('close');
                }
            });
        },
    }
});


App.IndexRoute = Ember.Route.extend({
    model: function() {

        return new Ember.RSVP.Promise(function(resolve) {

            var result = Ember.Object.create({
                newest_content: [],
                rated_content: [],
            });

            Ember.$.getJSON('api/v1/contents?filter=newest&limit=3', function(response) {
                for(var i=0; i < response.entity.length; i++) {
                    response.entity[i].image = 'images/thumb/' + response.entity[i].id + '.png';
                }
                result.set('newest_content', response.entity);
            }).then(function() {
                Ember.$.getJSON('api/v1/contents?filter=rated&limit=3', function(response) {
                    for(var i=0; i < response.entity.length; i++) {
                        response.entity[i].image = 'images/thumb/' + response.entity[i].id + '.png';
                    }
                    result.set('rated_content', response.entity);

                    resolve(result);
                });
            });
        });
    }
});


App.RegisterRoute = Ember.Route.extend({
    model: function() {
        return {};
    },
    activate: function() {
        if (this.get('controller') != undefined) {
            this.get('controller').resetMessage();
        }
    }
});


App.NewestRoute = Ember.Route.extend({
    loaded: 0,
    model: function() {
        
        var result = new Ember.A();
        var that = this;
        Ember.$.getJSON('api/v1/contents?filter=newest&limit=6', function(response) {
            var rows = [];
            for(var i=0; i < response.entity.length; i++) {
                response.entity[i].image = 'images/thumb/' + response.entity[i].id + '.png';
            }
            for(var i = 0; i < response.entity.length / 3; i++) {
                var row_content = [];
                for(var j = 0; j < 3; j++) {
                    if (response.entity[i*3 + j] == undefined) {
                        break;
                    }
                    row_content.push(response.entity[i*3 + j]);
                }
                rows.push(row_content);
            }
            that.set('loaded', response.entity.length);
            result.set('rows', rows);
        });

        return result;
    },
    actions: {
        load_more: function() {
            var offset = this.get('loaded');
            var that = this;

            Ember.$.getJSON('api/v1/contents?filter=newest&offset=' + offset + '&limit=6', function(response) {
                var result = that.modelFor('newest');

                for(var i = 0; i < response.entity.length / 3; i++) {
                    var row_content = [];
                    for(var j = 0; j < 3; j++) {
                        if (response.entity[i*3 + j] == undefined) {
                            break;
                        }
                        row_content.push(response.entity[i*3 + j]);
                    }
                    result.rows.pushObject(row_content);
                }

                that.set('loaded', offset + response.entity.length);
            });
        }
    }
});


App.TopRoute = Ember.Route.extend({
    loaded: 0,

    model: function() {
        
        var result = new Ember.Object();
        var that = this;
        Ember.$.getJSON('api/v1/contents?filter=rated', function(response) {
            var rows = [];
            for(var i=0; i < response.entity.length; i++) {
                response.entity[i].image = 'images/thumb/' + response.entity[i].id + '.png';
            }
            for(var i = 0; i < response.entity.length / 3; i++) {
                var row_content = [];
                for(var j = 0; j < 3; j++) {
                    if (response.entity[i*3 + j] == undefined) {
                        break;
                    }
                    row_content.push(response.entity[i*3 + j]);
                }
                rows.push(row_content);
            }
            that.set('loaded', response.entity.length);
            result.set('rows', rows);
        });

        return result;
    },
    actions: {
        load_more: function() {
            var offset = this.get('loaded');
            var that = this;

            Ember.$.getJSON('api/v1/contents?filter=rated&offset=' + offset + '&limit=6', function(response) {
                var result = that.modelFor('rated');

                for(var i = 0; i < response.entity.length / 3; i++) {
                    var row_content = [];
                    for(var j = 0; j < 3; j++) {
                        if (response.entity[i*3 + j] == undefined) {
                            break;
                        }
                        row_content.push(response.entity[i*3 + j]);
                    }
                    result.rows.pushObject(row_content);
                }

                that.set('loaded', offset + response.entity.length);
            });
        }
    }
});


App.TagsRoute = Ember.Route.extend({
    model: function(options) {

        this.controllerFor('tags').set('tag', options.tag);

        var result = new Ember.A();
        var that = this;
        Ember.$.getJSON('api/v1/contents?filter=' + options.tag + '&limit=6', function(response) {
            var rows = [];
            for(var i=0; i < response.entity.length; i++) {
                response.entity[i].image = 'images/thumb/' + response.entity[i].id + '.png';
            }
            for(var i = 0; i < response.entity.length / 3; i++) {
                var row_content = [];
                for(var j = 0; j < 3; j++) {
                    if (response.entity[i*3 + j] == undefined) {
                        break;
                    }
                    row_content.push(response.entity[i*3 + j]);
                }
                rows.push(row_content);
            }
            that.set('has_content', response.entity.length > 0);
            result.set('rows', rows);
        });

        return result;   
    }
});


// ==========================================
//  Users 
// ==========================================

App.UserSettingsRoute = Ember.Route.extend({
    model: function() {
        var user = App.get('user');
        var result = new Ember.Object();
        Ember.$.getJSON('api/v1/users/' + user.id, function(response) {
            result.setProperties(response);
            App.set('user', result);
        });
        return result;
    },
    actions: {
        save: function(user) {
            var controller = this.get('controller');
            var _user = JSON.parse(JSON.stringify(user));
            Ember.$.post('api/v1/users/' + user.id, _user).done(function(response) {
                controller.showMessage('User settings updated.', true);
            }).fail(function(response) {
                controller.showMessage(response.message, false);
            });
        }
    },
    activate: function() {
        if (this.get('controller') != undefined)
            this.get('controller').resetMessage();
    }
});


// ==========================================
//  Admin 
// ==========================================

App.AdminRoute = Ember.Route.extend({
    beforeModel: function() {
        this.transitionTo('admin.users');
    }
});


App.AdminUsersRoute = Ember.Route.extend({
    model: function() {
        var route = this;
        Ember.$.getJSON('api/v1/groups', function(response) {
            route.set('groups', response.entity);
        });
        Ember.$.getJSON('api/v1/roles', function(response) {
            route.set('roles', response);
        });
        return new Ember.RSVP.Promise(function(resolve) {
            Ember.$.getJSON('api/v1/users', function(response) {
                for(var i = 0; i < response.entity.length; i++) {
                    response.entity[i].createdAt = moment(response.entity[i].createdAt).format("MMM Do YYYY");
                }
                resolve(response);
            });
        });
    },
    setupController: function(controller, model) {
        this._super(controller, model);
        controller.set('groups', this.get('groups'));
        controller.set('roles', this.get('roles'));
    },
    renderTemplate: function(controller, model) {
        this.render('admin-user-list');
    }, 
    activate: function() {
        if (this.get('controller') != undefined) {
            this.get('controller').resetMessage();
        }
    },
    actions: {
        show_add_user: function() {
            this.get('controller').resetMessage();
            this.get('controller').set('user', {});
            this.get('controller').set('user_roles', []);
            this.get('controller').set('user_groups', []);
            this.render('admin-user-add');
        },
        show_edit_user: function() {
            this.get('controller').resetMessage();
            this.render('admin-user-edit');
        },
        show_list: function() {
            this.render('admin-user-list');
        }
    }
});


App.AdminGroupsRoute = Ember.Route.extend({
    model: function() {
        var route = this;
        Ember.$.getJSON('api/v1/roles', function(response) {
            route.set('roles', response);
        });
        return new Ember.RSVP.Promise(function(resolve) {
            Ember.$.getJSON('api/v1/groups', function(response) {
                for(var i = 0; i < response.entity.length; i++) {
                    response.entity[i].createdAt = moment(response.entity[i].createdAt).format("MMM Do YYYY");
                }
                resolve(response);
            });
        });
    },
    setupController: function(controller, model) {
        this._super(controller, model);
        controller.set('roles', this.get('roles'));
    },
    renderTemplate: function(controller, model) {
        this.render('admin-group-list');
    }, 
    activate: function() {
        if (this.get('controller') != undefined) {
            this.get('controller').resetMessage();
        }
    },
    actions: {
        show_add_group: function() {
            this.get('controller').resetMessage();
            this.get('controller').set('group', {});
            this.get('controller').set('group_roles', []);
            this.render('admin-group-add');
        },
        show_edit_group: function() {
            this.get('controller').resetMessage();
            this.render('admin-group-edit');
        },
        show_list: function() {
            this.render('admin-group-list');
        }
    }
});


App.AdminContentsRoute = Ember.Route.extend({
    model: function() {
        var route = this;
        Ember.$.getJSON('api/v1/tags', function(response) {
            route.set('tags', response);
        });
        return new Ember.RSVP.Promise(function(resolve) {
            Ember.$.getJSON('api/v1/contents', function(response) {
                for(var i = 0; i < response.entity.length; i++) {
                    response.entity[i].createdAt = moment(response.entity[i].createdAt).format("MMM Do YYYY");
                }
                resolve(response);
            });
        });
    },
    setupController: function(controller, model) {
        this._super(controller, model);
        controller.set('tags', this.get('tags'));
    },
    renderTemplate: function(controller, model) {
        this.render('admin-contents-list');
    }, 
    activate: function() {
        if (this.get('controller') != undefined) {
            this.get('controller').resetMessage();
        }
    },
    actions: {
        show_add_content: function() {
            this.get('controller').resetMessage();
            this.get('controller').set('content1', {});
            this.get('controller').set('content_tags', []);
            this.render('admin-content-add');
        },
        show_edit_content: function() {
            this.get('controller').resetMessage();
            this.render('admin-content-edit');
        },
        show_list: function() {
            this.render('admin-contents-list');
        }
    }
});


// ============================================
//  Content
// ============================================

App.ContentRoute = Ember.Route.extend({
    model: function(params) {
        var route = this;
        return Ember.RSVP.Promise(function(resolve) {
            Ember.$.getJSON('api/v1/contents/' + params.id, function(content) {
                var result = new Ember.Object();
                result.setProperties(content);
                result.set('score', 0);
                route.setupRating(result);
                route.setupComments(result);
                resolve(result);
            });
        });
    },
    afterModel: function(model) {
        if (model == undefined || model.id == undefined) {
            return;
        }
        this.setupRating(model);
        this.setupComments(model);
    },
    setupRating: function(content) {
        Ember.$.getJSON('api/v1/contents/' + content.id + '/rating', function(response) {
            Ember.set(content, 'score', Math.round(response * 100) / 100);
        });
    },  
    setupComments: function(content) {
        var comments = this.controllerFor('comments');
        comments.set('content_id', content.id);

        Ember.$.getJSON('api/v1/comments/' + content.id + '?limit=6', function(response) {
            comments.set('limit', 6);
            comments.set('comments', response.entity);
        });
    }
});


App.ContentIndexRoute = Ember.Route.extend({
    model: function(params) {
        return this.modelFor('content');
    }
});


App.ContentWatchRoute = Ember.Route.extend({
    model: function() {
        return this.modelFor('content');
    },
    afterModel: function() {
        return this.set('content', this.modelFor('content'));
    },
    setupController: function(controller, model) {
        this._super(controller, this.get('content'));
    }
});