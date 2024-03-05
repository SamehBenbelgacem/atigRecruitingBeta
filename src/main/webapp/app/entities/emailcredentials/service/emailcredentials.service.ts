import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmailcredentials, NewEmailcredentials } from '../emailcredentials.model';

export type PartialUpdateEmailcredentials = Partial<IEmailcredentials> & Pick<IEmailcredentials, 'id'>;

export type EntityResponseType = HttpResponse<IEmailcredentials>;
export type EntityArrayResponseType = HttpResponse<IEmailcredentials[]>;

@Injectable({ providedIn: 'root' })
export class EmailcredentialsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/emailcredentials');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(emailcredentials: NewEmailcredentials): Observable<EntityResponseType> {
    return this.http.post<IEmailcredentials>(this.resourceUrl, emailcredentials, { observe: 'response' });
  }

  update(emailcredentials: IEmailcredentials): Observable<EntityResponseType> {
    return this.http.put<IEmailcredentials>(
      `${this.resourceUrl}/${this.getEmailcredentialsIdentifier(emailcredentials)}`,
      emailcredentials,
      { observe: 'response' },
    );
  }

  partialUpdate(emailcredentials: PartialUpdateEmailcredentials): Observable<EntityResponseType> {
    return this.http.patch<IEmailcredentials>(
      `${this.resourceUrl}/${this.getEmailcredentialsIdentifier(emailcredentials)}`,
      emailcredentials,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmailcredentials>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmailcredentials[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEmailcredentialsIdentifier(emailcredentials: Pick<IEmailcredentials, 'id'>): number {
    return emailcredentials.id;
  }

  compareEmailcredentials(o1: Pick<IEmailcredentials, 'id'> | null, o2: Pick<IEmailcredentials, 'id'> | null): boolean {
    return o1 && o2 ? this.getEmailcredentialsIdentifier(o1) === this.getEmailcredentialsIdentifier(o2) : o1 === o2;
  }

  addEmailcredentialsToCollectionIfMissing<Type extends Pick<IEmailcredentials, 'id'>>(
    emailcredentialsCollection: Type[],
    ...emailcredentialsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const emailcredentials: Type[] = emailcredentialsToCheck.filter(isPresent);
    if (emailcredentials.length > 0) {
      const emailcredentialsCollectionIdentifiers = emailcredentialsCollection.map(
        emailcredentialsItem => this.getEmailcredentialsIdentifier(emailcredentialsItem)!,
      );
      const emailcredentialsToAdd = emailcredentials.filter(emailcredentialsItem => {
        const emailcredentialsIdentifier = this.getEmailcredentialsIdentifier(emailcredentialsItem);
        if (emailcredentialsCollectionIdentifiers.includes(emailcredentialsIdentifier)) {
          return false;
        }
        emailcredentialsCollectionIdentifiers.push(emailcredentialsIdentifier);
        return true;
      });
      return [...emailcredentialsToAdd, ...emailcredentialsCollection];
    }
    return emailcredentialsCollection;
  }
}
