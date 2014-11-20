<div id="retweet-modal" class="modal fade in show" aria-hidden="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div id="retweet-modal-header" class="modal-header">
                <button id="retweet-modal-close" class="close retweet-modal-close-modal-class"
                        data-dismiss="modal" onclick="removeRetweetModal();">x</button>

                <h3>Re-tweet</h3></div>

            <div class="modal-body" id="retweet-modal-body-main">
                <p id="retweet-modal-text">
                    ${statusText}
                </p>
            </div>

            <div class="modal-footer ">
                <a href="javascript:void (0)"
                   class="btn btn-primary text-bold">Save</a>
                <a href="javascript:void (0)" onclick="removeRetweetModal();"
                   class="btn btn-primary text-bold">
                    Close</a>
            </div>

            %{--<p class="xxpadding f13 text-darkgrey successMsg hide">Updated successfully!</p>--}%
        </div>
    </div>
</div>

<div class="modal-backdrop in" id="retweet-modal-backdrop-modal"></div>

<script type="text/javascript">
    function removeRetweetModal() {
        $('#retweet-modal').remove();
        $('#retweet-modal-backdrop-modal').remove();
    }
</script>