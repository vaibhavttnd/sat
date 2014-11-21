<asset:javascript src="/plugins/chosen/chosen.proto.js"/>
<asset:javascript src="/plugins/chosen/chosen.jquery.js"/>
<asset:javascript src="/plugins/chosen/prism.js"/>
<asset:stylesheet src="/plugins/chosen/prism.css"/>
<asset:stylesheet src="/plugins/chosen/chosen.css"/>
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

                <p>
                    <select data-placeholder="Choose a Country..." class="chosen-select" style="width:350px;">
                        <option value="United States">United States</option>
                        <option value="South Georgia and The South Sandwich Islands">South Georgia and The South Sandwich Islands</option>
                        <option value="South Sudan">South Sudan</option>
                        <option value="Spain">Spain</option>
                        <option value="Sri Lanka">Sri Lanka</option>
                        <option value="Sudan">Sudan</option>
                        <option value="Suriname">Suriname</option>
                        <option value="Svalbard and Jan Mayen">Svalbard and Jan Mayen</option>
                        <option value="Swaziland">Swaziland</option>
                        <option value="Sweden">Sweden</option>
                        <option value="Switzerland">Switzerland</option>
                        <option value="Syrian Arab Republic">Syrian Arab Republic</option>
                        <option value="Taiwan, Province of China">Taiwan, Province of China</option>
                        <option value="Tajikistan">Tajikistan</option>
                        <option value="Tanzania, United Republic of">Tanzania, United Republic of</option>
                        <option value="Thailand">Thailand</option>
                        <option value="Timor-leste">Timor-leste</option>
                    </select>
                </p>
            </div>

            <div class="modal-footer ">
                <a href="javascript:void (0)"
                   class="btn btn-primary text-bold">Retweet</a>
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
        console.log("${categories}");
    }
    $(document).ready(function () {
        bindChosenSelectBox();
    });
</script>