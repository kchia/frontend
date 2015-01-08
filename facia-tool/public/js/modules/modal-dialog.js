/* globals _ */
define([
    'knockout',
    'utils/mediator'
], function (
    ko,
    mediator
) {
    function ModalDialog () {
        this.isOpen = ko.observable(true);

        this.templateName = ko.observable('confirm_breaking_changes');
        this.templateData = ko.observable({});
    }

    ModalDialog.prototype.confirm = function(config) {
        var deferred = new $.Deferred();

        this.templateName(config.name);
        this.templateData(_.extend(config.data, {
            ok: function () {
                deferred.resolve();
            },
            cancel: function () {
                deferred.reject();
            }
        }));
        this.isOpen(true);

        return deferred.promise();
    };

    return new ModalDialog();
});
