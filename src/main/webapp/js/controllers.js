
App.ApplicationController = Ember.Controller.extend({
    error: '',
    updateCurrentPath: function() {
        App.set('currentPath', this.get('currentPath'));
    }.observes('currentPath')
});


App.RegisterController = Ember.ObjectController.extend({
    needs: ['error-box'],
    actions: {
        sign_up: function(user) {
            var controller = this;
            Ember.$.post('api/v1/users', user).done(function(user) {
                controller.showMessage('You successfully registered. You can now login.', true);
            }).fail(function(response) {
                controller.showMessage('You are missing some fileds', false);
            });
        }
    }
});


App.TagsController = Ember.ArrayController.extend({
    has_content: function() {
        var rows = this.get('model').get('rows');
        if (rows != undefined && rows.length > 0) {
            return true;
        }
        return false;
    }.property('model.rows')
});


// ==========================================
//  Users 
// ==========================================

App.UserSettingsController = Ember.ObjectController.extend({
    needs: ['error-box']
});


// ==========================================
//  Admin 
// ==========================================

App.AdminUsersController = Ember.ObjectController.extend({
    needs: ['error-box'],
    groups: [],
    roles: [],
    actions: {
        edit_user: function(user) {
            var controller = this;
            this.set('user', user);
            this.set('user_roles', []);
            this.set('user_groups', []);
            Ember.$.getJSON('api/v1/roles/users/' + user.id, function(roles) {
                controller.set('user_roles', roles);
            });
            Ember.$.getJSON('api/v1/groups/users/' + user.id, function(groups) {
                controller.set('user_groups', groups);
            });
            this.send('show_edit_user');
        },
        add_user: function(user) {
            var controller = this;
            user.groups = this.get('user_groups');
            user.roles = this.get('user_roles');
            Ember.$.post('api/v1/users', user).done(function(user) {
                user.createdAt = moment(user.createdAt).format("MMM Do YYYY");
                controller.showMessage('User successfully added.', true);
                controller.get('model.entity').addObject(user);
            }).fail(function(response) {
                controller.showMessage(response.message, false);
            });
        },
        save_user: function(user) {
            var controller = this;
            user.groups = this.get('user_groups');
            user.roles = this.get('user_roles');
            Ember.$.post('api/v1/users/' + user.id, user).done(function(response) {
                controller.showMessage('User settings updated.', true);
            }).fail(function(response) {
                controller.showMessage(response.message, false);
            });
        },
        delete_user: function(user) {
            var controller = this;
            var users = controller.get('model.entity');
            if (confirm('Are you sure you want to delete this user?')) {
                App.deleteAjax('api/v1/users', user).always(function() {
                    users.removeObject(user);
                });
            }
        },
    }
});


App.AdminGroupsController = Ember.ObjectController.extend({
    needs: ['error-box'],
    roles: [],
    actions: {
        edit_group: function(group) {
            var controller = this;
            this.set('group', group);
            this.set('group_roles', []);
            Ember.$.getJSON('api/v1/roles/groups/' + group.id, function(roles) {
                controller.set('group_roles', roles);
            });
            this.send('show_edit_group');
        },
        add_group: function(group) {
            var controller = this;
            group.roles = this.get('group_roles');
            Ember.$.post('api/v1/groups', group).done(function(group) {
                group.createdAt = moment(group.createdAt).format("MMM Do YYYY");
                controller.showMessage('Group successfully added.', true);
                controller.get('model.entity').addObject(group);
            }).fail(function(response) {
                controller.showMessage(response.message, false);
            });
        },
        save_group: function(group) {
            var controller = this;
            group.roles = this.get('group_roles');
            Ember.$.post('api/v1/groups/' + group.id, group).done(function(response) {
                controller.showMessage('Group settings updated.', true);
            }).fail(function(response) {
                controller.showMessage(response.message, false);
            });
        },
        delete_group: function(group) {
            var controller = this;
            var groups = controller.get('model.entity');
            if (confirm('Are you sure you want to delete this group?')) {
                App.deleteAjax('api/v1/groups', group).always(function() {
                    groups.removeObject(group);
                });
            }
        },
    }
});


App.AdminContentsController = Ember.ObjectController.extend({
    needs: ['error-box'],
    actions: {
        edit_content: function(content) {
            var controller = this;

            Ember.$.getJSON('api/v1/contents/' + content.id, function(response) {
                controller.set('content_tags', response.tags);
            });

            this.set('content1', content);
            this.send('show_edit_content');
        },
        add_content: function(content) {
            var controller = this;
            content.tags = this.get('content_tags');
            Ember.$.post('api/v1/contents', content).done(function(Content) {
                controller.showMessage('Content successfully added.', true);
                controller.get('model.entity').addObject(content);
            }).fail(function(response) {
                controller.showMessage(response.message, false);
            });
        },
        save_content: function(content) {
            var controller = this;
            content.tags = this.get('content_tags');
            Ember.$.post('api/v1/contents/' + content.id, content).done(function(response) {
                controller.showMessage('Content settings updated.', true);
            }).fail(function(response) {
                controller.showMessage(response.message, false);
            });
        },
        delete_content: function(content) {
            var controller = this;
            var contents = controller.get('model.entity');
            if (confirm('Are you sure you want to delete this content?')) {
                App.deleteAjax('api/v1/contents', content).always(function() {
                    contents.removeObject(content);
                });
            }
        },
    }
});


// ============================================
//  Content
// ============================================

App.ContentController = Ember.ObjectController.extend();

App.ContentIndexController = Ember.ObjectController.extend({});

App.ContentWatchController = Ember.ObjectController.extend({
    movieSource: function() {
        if (this.get('model').hasOwnProperty('get'))
            return "http://localhost/eCinema/" + this.get('model').get('id');
        return "http://localhost/eCinema/" + this.get('model').id;
    }.property('model')
});


App.CommentsController = Ember.ObjectController.extend({
    content_id: -1,
    limit: 6,
    offset: function() {
        return this.get('comments').length;
    }.property('comments.@each'),
    comments: [],
    noMoreComments: false,
    reverseComments: function() {
        return this.get('comments');
    }.property('comments.@each'),
    actions: {
        comment: function() {
            var comment = document.getElementById('comment').value;
            var content_id = this.get('content_id');
            var controller = this;
            Ember.$.post('api/v1/comments/' + content_id, {'text': comment}).done(function(response) {
                controller.get('comments').insertAt(0, response);
            });
        },
        load_more: function() {
            var controller = this;
            var content_id = controller.get('content_id');
            var limit = controller.get('limit');
            var offset = controller.get('offset');

            Ember.$.getJSON('api/v1/comments/' + content_id + '?limit=' + limit + '&offset=' + offset, function(response) {
                controller.set('offset', offset + response.offset);

                if (response.entity.length != 0) { 
                    controller.get('comments').pushObjects(response.entity);
                } else {
                    controller.set('noMoreComments', true);
                }
            });
        }
    }
});



App.TestController = Ember.ObjectController.extend({
    actions: {
        test: function() {

        }
    }
});