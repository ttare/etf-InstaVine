
App.deleteAjax = function(url, data) {
    return Ember.$.ajax({
        url: url + '/' + data.id,
        type: 'DELETE',
        dataType: 'json'
    });
}


Ember.Handlebars.helper('format-date', function( date ) {
    return moment(date).fromNow();
});


App.ModalView = Ember.View.extend({  
    layoutName: 'modal_layout',
    didInsertElement: function() {
        var view = this;
        Ember.$('#myModal').modal().on('hidden.bs.modal', function(e) {
            view.onClose();
        });
    },
    onClose: function() {
        this.controller.send('close');
    },
    actions: {
        close: function() {
            onClose();
        }
    }
});


App.ErrorBoxController = Ember.ObjectController.extend({
    init: function() {
        this.set('message', '');
        this.set('isSuccessMessage', false);
        this.set('isErrorMessage', false);
    }
});

Ember.ObjectController.reopen({
    showMessage: function(message, isSuccessMessage) {
        this.set('controllers.error-box.message', message);
        this.set('controllers.error-box.isSuccessMessage', isSuccessMessage);
        this.set('controllers.error-box.isErrorMessage', !isSuccessMessage);
        window.scrollTo(0, 0);
    },
    resetMessage: function() {
        this.set('controllers.error-box.message', '');
        this.set('controllers.error-box.isSuccessMessage', false);
        this.set('controllers.error-box.isErrorMessage', false);
    }
});

App.ErrorBoxView = Ember.View.extend({
    didInsertElement: function() {
        var view = this;
        $("#alert").bind('closed.bs.alert', function () {
                consloe.log('ahha');
                view.controller.set('message', '');
            });
    }
});




$(function () {
    'use strict';
    // Change this to the location of your server-side upload handler:
    var url = 'upload/',
        uploadButton = $('<button/>')
            .addClass('btn btn-primary')
            .prop('disabled', false)
            .text('Click to upload')
            .on('click', function () {
                var $this = $(this),
                    data = $this.data();
                $this
                    .off('click')
                    .text('Abort')
                    .on('click', function () {
                        $this.remove();
                        data.abort();
                    });
                data.submit().always(function () {
                    $this.remove();
                });
            });
    $('#fileupload').fileupload({
        url: url,
        dataType: 'json',
        autoUpload: false,
        acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,
        maxFileSize: 50000000, // 50 MB
        // Enable image resizing, except for Android and Opera,
        // which actually support image resizing, but fail to
        // send Blob objects via XHR requests:
        disableImageResize: /Android(?!.*Chrome)|Opera/
            .test(window.navigator.userAgent),
        previewMaxWidth: 100,
        previewMaxHeight: 100,
        previewCrop: true
    }).on('fileuploadadd', function (e, data) {
        data.context = $('<div/>').appendTo('#files');
        $.each(data.files, function (index, file) {
            var node = $('<p/>')
                    .append($('<span/>').text(file.name));
            if (!index) {
                node
                    .append('<br>')
                    .append(uploadButton.clone(true).data(data));
            }
            node.appendTo(data.context);
            return;
        });
    }).on('fileuploadprogressall', function (e, data) {
        var progress = parseInt(data.loaded / data.total * 100, 10);
        $('#progress .progress-bar').css(
            'width',
            progress + '%'
        );
    }).on('fileuploaddone', function (e, data) {
        $.each(data.result.files, function (index, file) {
            if (file.url) {
                var link = $('<a>')
                    .attr('target', '_blank')
                    .prop('href', file.url);
                $(data.context.children()[index])
                    .wrap(link);
            } else if (file.error) {
                var error = $('<span class="text-danger"/>').text(file.error);
                $(data.context.children()[index])
                    .append('<br>')
                    .append(error);
            }
        });
    }).on('fileuploadfail', function (e, data) {
        $.each(data.files, function (index, file) {
            var error = $('<span class="text-danger"/>').text('File upload failed.');
            $(data.context.children()[index])
                .append('<br>')
                .append(error);
        });
    }).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');
});