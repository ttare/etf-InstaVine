
App.AutoSuggestComponent = Ember.Component.extend({
    init: function() {
        this._super();
        this.set('selectedTags', []);
    },

    availableTags: function() {
        var tags = Ember.copy(this.get('tags'));
        var selectedTags = this.get('selectedTags');
        for(var i = 0; i < selectedTags.length; i++) {
            tags.removeObject(selectedTags[i]);
        }
        return tags;
    }.property('selectedTags.@each'),

    has_tags_to_select: function() {
        return this.get('availableTags').length > 0;
    }.property('availableTags.@each'),

    actions: {
        add_tag: function(tag) {
            this.get('selectedTags').pushObject(tag);
        },
        remove_tag: function(tag) {
            this.get('selectedTags').removeObject(tag);
        }
    }
});