import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IObjectContainingFile, NewObjectContainingFile } from '../object-containing-file.model';

export type PartialUpdateObjectContainingFile = Partial<IObjectContainingFile> & Pick<IObjectContainingFile, 'id'>;

export type EntityResponseType = HttpResponse<IObjectContainingFile>;
export type EntityArrayResponseType = HttpResponse<IObjectContainingFile[]>;

@Injectable({ providedIn: 'root' })
export class ObjectContainingFileService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/object-containing-files');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(objectContainingFile: NewObjectContainingFile): Observable<EntityResponseType> {
    return this.http.post<IObjectContainingFile>(this.resourceUrl, objectContainingFile, { observe: 'response' });
  }

  update(objectContainingFile: IObjectContainingFile): Observable<EntityResponseType> {
    return this.http.put<IObjectContainingFile>(
      `${this.resourceUrl}/${this.getObjectContainingFileIdentifier(objectContainingFile)}`,
      objectContainingFile,
      { observe: 'response' },
    );
  }

  partialUpdate(objectContainingFile: PartialUpdateObjectContainingFile): Observable<EntityResponseType> {
    return this.http.patch<IObjectContainingFile>(
      `${this.resourceUrl}/${this.getObjectContainingFileIdentifier(objectContainingFile)}`,
      objectContainingFile,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IObjectContainingFile>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IObjectContainingFile[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getObjectContainingFileIdentifier(objectContainingFile: Pick<IObjectContainingFile, 'id'>): number {
    return objectContainingFile.id;
  }

  compareObjectContainingFile(o1: Pick<IObjectContainingFile, 'id'> | null, o2: Pick<IObjectContainingFile, 'id'> | null): boolean {
    return o1 && o2 ? this.getObjectContainingFileIdentifier(o1) === this.getObjectContainingFileIdentifier(o2) : o1 === o2;
  }

  addObjectContainingFileToCollectionIfMissing<Type extends Pick<IObjectContainingFile, 'id'>>(
    objectContainingFileCollection: Type[],
    ...objectContainingFilesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const objectContainingFiles: Type[] = objectContainingFilesToCheck.filter(isPresent);
    if (objectContainingFiles.length > 0) {
      const objectContainingFileCollectionIdentifiers = objectContainingFileCollection.map(
        objectContainingFileItem => this.getObjectContainingFileIdentifier(objectContainingFileItem)!,
      );
      const objectContainingFilesToAdd = objectContainingFiles.filter(objectContainingFileItem => {
        const objectContainingFileIdentifier = this.getObjectContainingFileIdentifier(objectContainingFileItem);
        if (objectContainingFileCollectionIdentifiers.includes(objectContainingFileIdentifier)) {
          return false;
        }
        objectContainingFileCollectionIdentifiers.push(objectContainingFileIdentifier);
        return true;
      });
      return [...objectContainingFilesToAdd, ...objectContainingFileCollection];
    }
    return objectContainingFileCollection;
  }
}
