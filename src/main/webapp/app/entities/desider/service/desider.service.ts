import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDesider, NewDesider } from '../desider.model';

export type PartialUpdateDesider = Partial<IDesider> & Pick<IDesider, 'id'>;

export type EntityResponseType = HttpResponse<IDesider>;
export type EntityArrayResponseType = HttpResponse<IDesider[]>;

@Injectable({ providedIn: 'root' })
export class DesiderService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/desiders');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(desider: NewDesider): Observable<EntityResponseType> {
    return this.http.post<IDesider>(this.resourceUrl, desider, { observe: 'response' });
  }

  update(desider: IDesider): Observable<EntityResponseType> {
    return this.http.put<IDesider>(`${this.resourceUrl}/${this.getDesiderIdentifier(desider)}`, desider, { observe: 'response' });
  }

  partialUpdate(desider: PartialUpdateDesider): Observable<EntityResponseType> {
    return this.http.patch<IDesider>(`${this.resourceUrl}/${this.getDesiderIdentifier(desider)}`, desider, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDesider>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDesider[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDesiderIdentifier(desider: Pick<IDesider, 'id'>): number {
    return desider.id;
  }

  compareDesider(o1: Pick<IDesider, 'id'> | null, o2: Pick<IDesider, 'id'> | null): boolean {
    return o1 && o2 ? this.getDesiderIdentifier(o1) === this.getDesiderIdentifier(o2) : o1 === o2;
  }

  addDesiderToCollectionIfMissing<Type extends Pick<IDesider, 'id'>>(
    desiderCollection: Type[],
    ...desidersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const desiders: Type[] = desidersToCheck.filter(isPresent);
    if (desiders.length > 0) {
      const desiderCollectionIdentifiers = desiderCollection.map(desiderItem => this.getDesiderIdentifier(desiderItem)!);
      const desidersToAdd = desiders.filter(desiderItem => {
        const desiderIdentifier = this.getDesiderIdentifier(desiderItem);
        if (desiderCollectionIdentifiers.includes(desiderIdentifier)) {
          return false;
        }
        desiderCollectionIdentifiers.push(desiderIdentifier);
        return true;
      });
      return [...desidersToAdd, ...desiderCollection];
    }
    return desiderCollection;
  }
}
