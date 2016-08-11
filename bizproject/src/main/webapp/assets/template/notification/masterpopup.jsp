<div class="modal fade bs-example-modal-lgs" tabindex="-1" role="dialog"
	aria-labelledby="myLargeModalLabel">
	<div class="modal-dialog modal-lg">
		Modal content
		<div class="modal-contents">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h3 class="modal-title">Master Settings</h3>
			</div>
			<div class="modal-body">
				<div class="col-xs-12">
					<table class="table table-bordered table-hover" id="tab_logic">
						<thead>
							<tr>
								<th class="text-center">User</th>
								<th class="text-center">Department</th>
								<th class="text-center">Email Address</th>
								<th class="text-center"><i class="fa fa-plus-circle fa-2x"
									placeholder="Add Row" id="add_row"></i></th>
							</tr>
						</thead>
						<tr id='addr0' data-id="0" class="hidden">
							<td data-name="name"><input type="text" name='name0'
								placeholder='Enter UserName' class="form-control" disabled /></td>
							<td data-name="mail"><input type="text" name='mail0'
								placeholder='Enter Department Name' class="form-control"
								disabled /></td>
							<td data-name="desc"><input type="text" name='mail0'
								placeholder='Enter Email' class="form-control" disabled /></td>
							<td data-name="del"><i class="fa fa-edit fa-2x edit_row"
								id="edit_row"></i><i class="fa fa-save fa-2x save_row "
								style="display: none" id="save_row"></i><i
								class="fa fa-minus-circle fa-2x row-remove"
								placeholder="Delete Row"></i></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
