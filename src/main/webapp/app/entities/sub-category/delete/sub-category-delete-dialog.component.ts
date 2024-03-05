import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISubCategory } from '../sub-category.model';
import { SubCategoryService } from '../service/sub-category.service';

@Component({
  standalone: true,
  templateUrl: './sub-category-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SubCategoryDeleteDialogComponent {
  subCategory?: ISubCategory;

  constructor(
    protected subCategoryService: SubCategoryService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subCategoryService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
