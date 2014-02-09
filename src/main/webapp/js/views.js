
App.ApplicationView = Ember.View.extend({
    
});


App.ContentIndexView = Ember.View.extend({
    didInsertElement: function() {
        this.$(".rating").raty({ 
            score: function() {
                return $(this).attr('data-score');
            },
            path: 'images/',
            starOff: 'star-off-big.png',
            starOn : 'star-on-big.png',
            width: 200,
            halfShow : false,
            click: function(score) {
                var contentId = $(this).attr('data-content');
                Ember.$.post('api/v1/contents/' + contentId + '/rating?mark=' + score);
            }
        });
    }
});


App.ContentWatchView = Ember.View.extend({
    didInsertElement: function() {
        var popcorn = Popcorn("#video");
    }
});


App.SingleContentView = Ember.View.extend({    
    templateName: 'single_content',

    didInsertElement: function(){
        this.$('.description').dotdotdot();

        this.$('.content').hover(function() {
            $(this).addClass('hover');
        }, function() {
            $(this).removeClass('hover');
        });
    },
});


App.CatImageView = Ember.View.extend({
    tagName: 'img',
    classNames: ['img-rounded'],
    attributeBindings: ['alt', 'style', 'src', 'width', 'height'],
    alt: 'Image: 360x200',
    width: function() {
        var isLoaded = this.get('isLoaded');
        if (isLoaded) {
            return '100%';
        }
        return '16';
    }.property('isLoaded'),
    height: function() {
        var isLoaded = this.get('isLoaded');
        if (isLoaded) {
            return '61%';
        }
        return '16';
    }.property('isLoaded'),
    isLoaded: false,
    style: 'margin-left: 172px; margin-right: 172px; margin-top: 92px; margin-bottom: 92px',
    src: 'images/loader.gif',

    didInsertElement: function () {
        if (this.get('src') != 'images/loader.gif') {
            return;
        }

        var image = new Image();        
        image.src = this.get('source');

        var view = this;
        image.onload = function() {
            if (view.isDestroyed)
                return;
            view.set('style', 'max-width: 360px; max-height: 200px');
            view.set('src', this.src);
            view.set('isLoaded', true);
        }
    }
});


App.CrossfadeView = {
    didInsertElement: function(){
        this.$().hide().fadeIn(400);
    },
    willDestroyElement: function(){
        this.$().slideDown(250);
    }
};

App.IndexView = Ember.View.extend(App.CrossfadeView);

App.RegisterView = Ember.View.extend(App.CrossfadeView);

App.NewestView = Ember.View.extend(App.CrossfadeView);

App.TopView = Ember.View.extend(App.CrossfadeView);

App.NowView = Ember.View.extend(App.CrossfadeView);

App.TheatersView = Ember.View.extend(App.CrossfadeView);

App.ContentView = Ember.View.extend(App.CrossfadeView);

App.TagsView = Ember.View.extend(App.CrossfadeView);
