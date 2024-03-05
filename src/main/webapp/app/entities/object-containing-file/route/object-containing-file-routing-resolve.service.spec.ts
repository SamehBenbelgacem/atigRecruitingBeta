import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IObjectContainingFile } from '../object-containing-file.model';
import { ObjectContainingFileService } from '../service/object-containing-file.service';

import objectContainingFileResolve from './object-containing-file-routing-resolve.service';

describe('ObjectContainingFile routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: ObjectContainingFileService;
  let resultObjectContainingFile: IObjectContainingFile | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(ObjectContainingFileService);
    resultObjectContainingFile = undefined;
  });

  describe('resolve', () => {
    it('should return IObjectContainingFile returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        objectContainingFileResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultObjectContainingFile = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultObjectContainingFile).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        objectContainingFileResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultObjectContainingFile = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultObjectContainingFile).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IObjectContainingFile>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        objectContainingFileResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultObjectContainingFile = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultObjectContainingFile).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
